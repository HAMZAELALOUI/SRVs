import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Activite, Ville, Utilisateur, Reservationactivite } from "../../../services/types";
import { activiteService } from "../../../services/ActiviteService";
import { reservationService } from "../../../services/ReservationactiviteService";
import { paiementService } from "../../../services/PaiementService"; // Importer le service de paiement
import UtilisateurServices, { LoginResponse } from "../../../services/UtilisateurServices";
import {FaPaypal} from "react-icons/fa";

const ActiviteDetails: React.FC = () => {
    const navigate = useNavigate();
    const { id } = useParams<{ id: string }>();

    // States
    const [activite, setActivite] = useState<Activite | null>(null);
    const [ville, setVille] = useState<Ville | null>(null);
    const [showReservationPopup, setShowReservationPopup] = useState(false); // Nouveau state pour la popup de réservation
    const [showPaymentForm, setShowPaymentForm] = useState(false); // Nouveau state pour afficher le formulaire de paiement
    const [nombrePersonnes, setNombrePersonnes] = useState(1);
    const [prixTotal, setPrixTotal] = useState(0);
    const [reservationError, setReservationError] = useState<string | null>(null);
    const [user, setUser] = useState<Utilisateur | null>(null);
    const [formDataPaiement, setFormDataPaiement] = useState({
        modePaiement: "",
        montant: 0,
        numeroCarte: "",
        dateExpiration: "",
        titulaireCarte: "",
    });
    const [paiementSuccess, setPaiementSuccess] = useState(false);

    // Check if user is logged in
    const userIsLoggedIn = sessionStorage.getItem("authToken") !== null;

    // Fetch activite data
    useEffect(() => {
        const fetchData = async () => {
            try {
                const activiteData = await activiteService.findActiviteWithVilleById(Number(id));
                setActivite(activiteData);

                if (activiteData.ville && typeof activiteData.ville === 'object') {
                    const { nom, pays } = activiteData.ville;
                    setVille({ nom, pays });
                } else {
                    console.error('Ville data is not an object or is undefined:', activiteData.ville);
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        fetchData();
    }, [id]);

    // Fetch user data if logged in
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Fetch user data based on email stored in sessionStorage
                const email = sessionStorage.getItem("email");
                if (email) {
                    console.log("Fetching user data for email:", email);
                    const userData = await UtilisateurServices.getUserByEmail(email);
                    console.log("User data:", userData);
                    setUser(userData);
                }
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        if (userIsLoggedIn) {
            fetchUserData();
        }
    }, [userIsLoggedIn]);

    // Update total price when number of persons changes
    useEffect(() => {
        if (activite) {
            setPrixTotal(nombrePersonnes * activite.prix);
        }
    }, [nombrePersonnes, activite]);

    // Handle click on reservation button
    const handleReservationClick = () => {
        if (userIsLoggedIn) {
            setShowReservationPopup(true); // Afficher la popup de réservation
        } else {
            navigate('/sign-in');
        }
    };

    // Close reservation popup
    const closeReservationPopup = () => {
        setShowReservationPopup(false);
    };

    // Handle change in number of persons
    const handleNombrePersonnesChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = parseInt(e.target.value, 10);
        setNombrePersonnes(value);
    };

    // Handle confirmation of reservation
    const handleReservationConfirm = async () => {
        if (!user) {
            // Handle case where user data is not available
            return;
        }

        const reservationData: Reservationactivite = {
            activite: activite!,
            user: user!,
            nombrePersonnes: nombrePersonnes,
            prixTotal: prixTotal
        };

        try {
            const savedReservation = await reservationService.saveReservation(reservationData);
            console.log('Reservation saved:', savedReservation);
            setShowReservationPopup(false); // Fermer la popup de réservation
            setReservationError(null);
            setShowPaymentForm(true); // Afficher le formulaire de paiement après la réservation
        } catch (error) {
            console.error('Error saving reservation:', error);
            setReservationError('Une erreur est survenue lors de la réservation. Veuillez réessayer.');
        }
    };

    // Handle change in payment form fields
    const handleChangePaiement = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormDataPaiement({ ...formDataPaiement, [e.target.name]: e.target.value });
    };

    // Handle submission of payment form
    const handleSubmitPaiement = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (user && activite) {
            const paiementData = {
                ...formDataPaiement,
                reservationactivite: {
                    id: activite.id // Utilisation de l'ID de l'activité comme ID de réservationactivite
                },
                montant: prixTotal // Utilisation du prix total de l'activité comme montant
            };
            try {
                const savedPaiement = await paiementService.savePaiementActivite(paiementData);
                console.log('Paiement saved:', savedPaiement);
                setPaiementSuccess(true);
                setShowPaymentForm(false); // Fermer le formulaire de paiement après le paiement réussi
            } catch (error) {
                console.error('Error saving paiement:', error);
                // Gérer l'erreur de paiement
            }
        }
    };

    if (!activite || !ville) {
        return <div>Loading...</div>;
    }

    return (
        <div className="flex justify-center items-center h-screen pt-20">
            <div className="font-sans">
                <div className="relative sm:max-w-sm w-full">
                    <div className="card bg-orange-500 shadow-lg w-full h-full rounded-3xl absolute transform -rotate-6"></div>
                    <div className="card bg-orange-300 shadow-lg w-full h-full rounded-3xl absolute transform rotate-6"></div>
                    <div className="relative w-full rounded-3xl px-6 py-4 bg-gray-100 shadow-md">
                        <div className="relative">
                            <img src={activite.image} alt={activite.nom} className="w-full h-64 object-cover"/>
                            <div className="absolute inset-0 bg-black opacity-50"></div>
                        </div>
                        <div className="p-8 font-sans">
                            <h2 className="text-4xl font-bold mb-4 text-gray-900">{activite.nom}</h2>
                            <p className="text-lg text-gray-700 mb-2">Lieu: {activite.lieu}</p>
                            <p className="text-lg text-gray-700 mb-2">Description: {activite.description}</p>
                            <p className="text-lg text-gray-700 mb-2">Prix d'activité: {activite.prix}</p>
                            <p className="text-lg text-gray-700 mb-2">Date de départ: {activite.horaire}</p>
                            <p className="text-lg text-gray-700 mb-2">Nom de la ville: {ville.nom}</p>
                            <p className="text-lg text-gray-700 mb-4">Pays: {ville.pays}</p>
                            <div className="flex justify-center">
                                <button
                                    className="bg-orange-500 hover:bg-orange-700 text-white font-semibold py-3 px-6 rounded-full shadow-lg transition duration-300"
                                    onClick={handleReservationClick}
                                >
                                    Réserver maintenant
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            {showReservationPopup && !showPaymentForm && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                    <div className="bg-white p-8 rounded-lg shadow-xl">
                        <div className="flex justify-end">
                            <button onClick={closeReservationPopup}
                                    className="text-gray-700 hover:text-red-500 focus:outline-none">
                                X
                            </button>
                        </div>
                        <form>
                            <div className="mb-4">
                                <label htmlFor="nombrePersonnes" className="block text-sm font-medium text-gray-700">
                                    Nombre de personnes
                                </label>
                                <input
                                    type="number"
                                    id="nombrePersonnes"
                                    name="nombrePersonnes"
                                    className="mt-1 p-2 w-full border-gray-300 rounded-md"
                                    value={nombrePersonnes}
                                    onChange={handleNombrePersonnesChange}
                                />
                            </div>
                            <div className="mb-4">
                                <label className="block text-sm font-medium text-gray-700">
                                    Prix total
                                </label>
                                <p className="mt-1 p-2 w-full border-gray-300 rounded-md">{prixTotal}</p>
                            </div>
                            <div className="flex justify-end">
                                <button
                                    type="button"
                                    className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-500 hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                                    onClick={handleReservationConfirm}
                                >
                                    Confirmer la réservation
                                </button>
                            </div>
                            {reservationError && (
                                <p className="text-red-500 mt-4">{reservationError}</p>
                            )}
                        </form>
                    </div>
                </div>
            )}
            {showPaymentForm && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
                    <div className="bg-white p-8 rounded-lg shadow-xl">
                        <div className="flex justify-end">
                            <button onClick={() => setShowPaymentForm(false)}
                                    className="text-gray-700 hover:text-red-500 focus:outline-none">
                                X
                            </button>
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

                        {reservationError && (
                            <p className="text-red-500 mt-4">{reservationError}</p>
                        )}
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

export default ActiviteDetails;