import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { hotelService } from "../../../services/HotelService";
import { reservationService } from "../../../services/ReservationService";
import { paiementService } from "../../../services/PaiementService"; // Importez le service de paiement
import { Hotel, Reservation } from "../../../services/types";
import {FaPaypal} from "react-icons/fa";

const HotelDetails: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [hotel, setHotel] = useState<Hotel | null>(null);
    const [reservationSuccess, setReservationSuccess] = useState<boolean>(false); // État pour contrôler l'affichage de la confirmation de réservation
    const [reservation, setReservation] = useState<Reservation | null>(null); // État pour stocker les données de réservation
    const [formDataPaiement, setFormDataPaiement] = useState({
        modePaiement: "",
        montant: 0,
        numeroCarte: "",
        dateExpiration: "",
        titulaireCarte: "",
    });
    const [paiementSuccess, setPaiementSuccess] = useState(false);


    useEffect(() => {
        fetchHotelDetails();
    }, []);

    const fetchHotelDetails = async () => {
        try {
            const hotelData = await hotelService.findActiviteWithVilleById(parseInt(id ?? ""));
            setHotel(hotelData);
        } catch (error) {
            console.error("Error fetching hotel details:", error);
        }
    };

    const handleReservation = async () => {
        if (hotel) {
            try {
                const savedReservation = await reservationService.saveReservation(hotel.id);
                if (savedReservation) {
                    console.log("Reservation saved:", savedReservation);
                    setReservation(savedReservation); // Stocker les données de réservation
                    setReservationSuccess(true); // Définir l'état de confirmation de réservation sur true
                } else {
                    console.log("Failed to save reservation: Hotel already booked");
                    // Afficher un message d'erreur à l'utilisateur
                }
            } catch (error) {
                console.error("Error saving reservation:", error);
                // Afficher un message d'erreur à l'utilisateur
            }
        }
    };

    const handleChangePaiement = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormDataPaiement({ ...formDataPaiement, [e.target.name]: e.target.value });
    };

    const handleSubmitPaiement = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (reservation) {
            try {
                const paiementData = { // Créer les données du paiement à partir des données de réservation
                    ...formDataPaiement,
                    reservation: reservation
                };
                const savedPaiement = await paiementService.savePaiement(paiementData);
                if (savedPaiement) {
                    console.log("Paiement saved:", savedPaiement);
                    setReservationSuccess(false); // Réinitialiser l'état de confirmation de réservation
                    setPaiementSuccess(true);

                } else {
                    console.log("Failed to save paiement");
                    // Afficher un message d'erreur à l'utilisateur
                }
            } catch (error) {
                console.error("Error saving paiement:", error);
                // Afficher un message d'erreur à l'utilisateur
            }
        }
    };

    if (!hotel) {
        return <div>Loading...</div>;
    }

    return (
        <div className="flex justify-center items-center h-screen pt-20">
            <div className="font-sans">
                <div className="relative sm:max-w-sm w-full">
                    <div
                        className="card bg-orange-500 shadow-lg w-full h-full rounded-3xl absolute transform -rotate-6 shadow-orange-400"></div>
                    <div
                        className="card bg-orange-300 shadow-lg w-full h-full rounded-3xl absolute transform rotate-6  "></div>
                    <div className="relative w-full  px-6 py-4 bg-gray-100 shadow-md  ">
                        <div className="rounded-full overflow-hidden border-4 border-orange-500 ">
                            <img className="w-full h-full object-cover object-center" src={hotel.image} alt="Hotel"/>
                        </div>
                        <div className=""></div>
                        <div className="p-8 font-sans">
                            <h2 className="text-4xl font-bold mb-4 text-gray-900">{hotel.nom}</h2>
                            <p className="text-lg text-gray-700 mb-2">Lieu: {hotel.emplacement}</p>
                            <p className="text-lg text-gray-700 mb-2">Description: {hotel.description}</p>
                            <p className="text-lg text-gray-700 mb-2">Prix d'activité: {hotel.prixChambres}</p>
                            <p className="text-lg text-gray-700 mb-2">Date de départ: {hotel.dateD}</p>
                            <p className="text-lg text-gray-700 mb-2">Nombre d'étoiles: {hotel.nombreEtoiles}</p>
                            <div className="flex justify-center">
                                <button
                                    className="bg-gradient-to-r from-orange-300 to-orange-500 text-white font-medium px-6 py-2 rounded-full shadow-md hover:from-orange-500 hover:to-orange-700 transition duration-300 focus:outline-none"
                                    onClick={handleReservation}
                                >
                                    Réserver maintenant
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


    {
        reservationSuccess && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                <div className="bg-white p-8 rounded-lg shadow-xl">
                    <div className="flex justify-end">

                    </div>

                    <h1 className="text-2xl font-medium text-gray-700 sm:text-3xl">Secure Checkout<span
                        className="mt-2 block h-1 w-10 bg-orange-500 sm:w-20"></span></h1>
                    <form onSubmit={handleSubmitPaiement} className="mt-0 flex flex-col space-y-4">
                        <div>
                            <label htmlFor="modePaiement" className="text-xs font-semibold text-gray-500">Mode de
                                paiement</label>
                            <div className="relative">
                                <input
                                    type="text"
                                    id="modePaiement"
                                    name="modePaiement"
                                    value={formDataPaiement.modePaiement}
                                    onChange={handleChangePaiement}
                                    required
                                    placeholder="Mode de paiement"
                                    className="mt-1 block w-full rounded border-gray-300 bg-gray-50 py-3 px-4 text-sm placeholder-gray-300 shadow-sm outline-none transition focus:ring-2 focus:ring-teal-500"
                                />
                                {/* Utilisation de l'icône PayPal en bleu */}
                                <FaPaypal className="absolute bottom-3 right-3 max-h-4 text-blue-500"/>
                            </div>
                        </div>
                        {/* Ajoutez des sections similaires pour chaque champ de saisie */}
                            <div>
                                <label htmlFor="montant" className="text-xs font-semibold text-gray-600">Montant:</label>
                                <input
                                    type="number"
                                    id="montant"
                                    name="montant"
                                    value={formDataPaiement.montant}
                                    onChange={handleChangePaiement}
                                    required
                                    placeholder="Montant"
                                    className="mt-1 block w-full rounded border-gray-300 bg-gray-50 py-3 px-4 text-sm placeholder-gray-300 shadow-sm outline-none transition focus:ring-2 focus:ring-teal-500"
                                />
                            </div>
                            <div>
                                <label htmlFor="numeroCarte" className="text-xs font-semibold text-gray-600">Numéro de
                                    carte:</label>
                                <input
                                    type="text"
                                    id="numeroCarte"
                                    name="numeroCarte"
                                    value={formDataPaiement.numeroCarte}
                                    onChange={handleChangePaiement}
                                    required
                                    placeholder="1234-5678-XXXX-XXXX"
                                    className="mt-1 block w-full rounded border-gray-300 bg-gray-50 py-3 px-4 text-sm placeholder-gray-300 shadow-sm outline-none transition focus:ring-2 focus:ring-teal-500"
                                />
                            </div>
                            <div>
                                <label htmlFor="dateExpiration" className="text-xs font-semibold text-gray-600">Date
                                    d'expiration:</label>
                                <input
                                    type="text"
                                    id="dateExpiration"
                                    name="dateExpiration"
                                    value={formDataPaiement.dateExpiration}
                                    onChange={handleChangePaiement}
                                    required
                                    placeholder="MM/YY"
                                    className="mt-1 block w-full rounded border-gray-300 bg-gray-50 py-3 px-4 text-sm placeholder-gray-300 shadow-sm outline-none transition focus:ring-2 focus:ring-teal-500"
                                />
                            </div>
                            <div>
                                <label htmlFor="titulaireCarte" className="text-xs font-semibold text-gray-600">Titulaire de la
                                    carte:</label>
                                <input
                                    type="text"
                                    id="titulaireCarte"
                                    name="titulaireCarte"
                                    value={formDataPaiement.titulaireCarte}
                                    onChange={handleChangePaiement}
                                    required
                                    placeholder="Titulaire de la carte"
                                    className="mt-1 block w-full rounded border-gray-300 bg-gray-50 py-3 px-4 text-sm placeholder-gray-300 shadow-sm outline-none transition focus:ring-2 focus:ring-teal-500"
                                />
                            </div>
                            <div className="flex justify-center">
                                <button
                                    type="submit"
                                    className="inline-flex items-center justify-center rounded-full bg-orange-500 py-2.5 px-6 text-base font-semibold tracking-wide text-white text-opacity-80 outline-none ring-offset-2 transition hover:text-opacity-100 focus:ring-2 focus:ring-orange-600 sm:text-lg"
                                >
                                    Payer
                                </button>
                            </div>
                        </form>
                        <p className="mt-9 text-center text-sm font-semibold text-gray-500">By placing this order you agree to
                            the <a href="#" className="whitespace-nowrap text-teal-400 underline hover:text-teal-600">Terms and
                                Conditions</a></p>


                    </div>
                </div>
            )}
            {paiementSuccess && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                    <div className="bg-white p-8 rounded-lg shadow-xl">
                        <h2 className="text-xl font-semibold mb-4 text-gray-800">Paiement réussi</h2>
                        <p className="text-lg text-gray-700">Votre paiement a été effectué avec succès.</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default HotelDetails;
