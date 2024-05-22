import React, { useEffect, useState } from "react";
import { useLocation } from 'react-router-dom';
import { reservationVolService } from "../../../services/ReservationVolService";
import { paiementService } from "../../../services/PaiementService";
import { Vol, ReservationVol } from "../../../services/types";
import {FaPaypal} from "react-icons/fa";

const FlightDetails: React.FC = () => {
    const location = useLocation();
    const { vol } = location.state as { vol: Vol };

    const [reservationSuccess, setReservationSuccess] = useState<boolean>(false);
    const [reservation, setReservation] = useState<ReservationVol | null>(null);
    const [formDataPaiement, setFormDataPaiement] = useState({
        modePaiement: "",
        montant: 0,
        numeroCarte: "",
        dateExpiration: "",
        titulaireCarte: "",
    });
    const [paiementSuccess, setPaiementSuccess] = useState(false);


    const handleStartBooking = async () => {
        if (vol) {
            try {
                const savedReservation = await reservationVolService.saveReservationVol({
                    volId: vol.idVol,
                });
                if (savedReservation) {
                    console.log("Reservation saved:", savedReservation);
                    setReservation(savedReservation);
                    setReservationSuccess(true);
                } else {
                    console.log("Failed to save reservation");
                }
            } catch (error) {
                console.error("Error saving reservation:", error);
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
                const paiementData = {
                    ...formDataPaiement,
                    reservationVol: reservation // Utilisation de reservationVol au lieu de reservation
                };
                const savedPaiement = await paiementService.savePaiementVol(paiementData); // Appel de savePaiementVol
                if (savedPaiement) {
                    console.log("Paiement saved:", savedPaiement);
                    setReservationSuccess(false);
                    setPaiementSuccess(true);

                } else {
                    console.log("Failed to save paiement");
                }
            } catch (error) {
                console.error("Error saving paiement:", error);
            }
        }
    };

    return (
        <div className="container m-40 p-4 lg:flex lg:items-start lg:space-x-8">
            <div className="lg:flex-shrink-0">
                <img className="w-full h-96 object-cover lg:w-96 lg:h-auto rounded-lg shadow-md" src={`http://localhost:8080${vol.imageUrl}`} alt="Flight" />
            </div>
            <div className="mt-4 lg:mt-0 lg:flex-grow">
                <h1 className="text-2xl font-bold text-gray-900">{`Flight from ${vol.origin.nom} to ${vol.destination.nom}`}</h1>
                <section aria-labelledby="information-heading" className="mt-3">
                    <h2 id="information-heading" className="sr-only">
                        Flight information
                    </h2>

                    <div className="mt-6">
                        <div className="flex items-center">
                            <h4 className="flex-shrink-0 pr-4 bg-white text-sm tracking-wider font-semibold uppercase text-color5">
                                Details
                            </h4>
                            <div className="flex-1 border-t-2 border-gray-200"></div>
                        </div>
                        <div className="mt-6 flow-root">
                            <dl className="-mb-8 divide-y divide-gray-200">
                                <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
                                    <dt className="text-sm font-medium text-gray-500">Departure Date</dt>
                                    <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                                        {new Date(vol.heureDepart).toLocaleDateString()}
                                    </dd>
                                </div>
                                <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
                                    <dt className="text-sm font-medium text-gray-500">Arrival Date</dt>
                                    <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                                        {new Date(vol.heureArrivee).toLocaleDateString()}
                                    </dd>
                                </div>
                                <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
                                    <dt className="text-sm font-medium text-gray-500">Price</dt>
                                    <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                                        ${vol.prix.toFixed(2)}
                                    </dd>
                                </div>
                                <div className="py-4 sm:py-5 sm:grid sm:grid-cols-3 sm:gap-4">
                                    <dt className="text-sm font-medium text-gray-500">Available Seats</dt>
                                    <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
                                        {vol.placesDisponibles}
                                    </dd>
                                </div>
                            </dl>
                        </div>
                    </div>
                </section>
                {/* Buttons or other actions */}
                <div className="mt-10">
                    <button
                        onClick={handleStartBooking}
                        className="bg-color1 border border-transparent rounded-md py-2 px-4 flex items-center justify-center text-sm font-medium text-white hover:bg-color2 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-color2"
                    >
                        Start Booking
                    </button>
                </div>
                {reservationSuccess && (
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
        </div>
    );
};

export default FlightDetails;