import React, { useEffect, useState } from "react";
import { Activite, Ville } from "../../../services/types";
import villeService from "../../../services/VilleService";
import Swal from 'sweetalert2';

const EditActiviteForm: React.FC<{
    activite: Activite;
    onClose: () => void;
    onSave: (id: number, activiteData: Activite) => Promise<void>;
}> = ({ activite, onClose, onSave }) => {
    const [nom, setNom] = useState<string>(activite.nom);
    const [lieu, setLieu] = useState<string>(activite.lieu);
    const [description, setDescription] = useState<string>(activite.description);
    const [horaire, setHoraire] = useState<string>(activite.horaire);
    const [prix, setPrix] = useState<number>(activite.prix);
    const [image, setImage] = useState<string>(activite.image);
    const [dateDebut, setDateDebut] = useState<string>(activite.dateDebut);
    const [dateFin, setDateFin] = useState<string>(activite.dateFin);
    const [villeId, setVilleId] = useState<number | string>(activite.ville?.id || '');
    const [villes, setVilles] = useState<Ville[]>([]);
    const [selectedVille, setSelectedVille] = useState<Ville | null>(null);

    useEffect(() => {
        villeService.getAllVilles()
            .then(data => setVilles(data))
            .catch(error => {
                console.error('Error fetching cities:', error);
                setVilles([]);
            });
    }, []);

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        // Check if villeId is null before submitting
        if (!villeId) {
            // Handle case where villeId is null (e.g., show an error message)
            console.error("Ville ID is null");
            return; // Do not proceed with saving
        }

        const activiteData = {
            nom,
            lieu,
            description,
            horaire,
            prix,
            image,
            dateDebut,
            dateFin,
            ville: selectedVille
        };

        Swal.fire({
            title: "Do you want to save the changes?",
            showDenyButton: true,
            showCancelButton: true,
            confirmButtonText: "Save",
            denyButtonText: "Don't save",
            icon: 'question'
        }).then(async (result) => {
            if (result.isConfirmed) {
                await onSave(activite.id!, activiteData);
                Swal.fire("Saved!", "", "success");
            } else if (result.isDenied) {
                Swal.fire("Changes are not saved", "", "info");
            }
        });
    };

    return (
        <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-5 rounded-lg shadow-lg max-w-lg w-full">
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label htmlFor="image" className="block text-sm font-medium text-gray-700">Image URL</label>
                        <input
                            type="text"
                            id="image"
                            value={image}
                            onChange={(e) => setImage(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="nom" className="block text-sm font-medium text-gray-700">Activite Name</label>
                        <input
                            type="text"
                            id="nom"
                            value={nom}
                            onChange={(e) => setNom(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="lieu" className="block text-sm font-medium text-gray-700">Location</label>
                        <input
                            type="text"
                            id="lieu"
                            value={lieu}
                            onChange={(e) => setLieu(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="description" className="block text-sm font-medium text-gray-700">Description</label>
                        <textarea
                            id="description"
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="horaire" className="block text-sm font-medium text-gray-700">Schedule</label>
                        <input
                            type="text"
                            id="horaire"
                            value={horaire}
                            onChange={(e) => setHoraire(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="prix" className="block text-sm font-medium text-gray-700">Price</label>
                        <input
                            type="number"
                            id="prix"
                            value={prix}
                            onChange={(e) => setPrix(Number(e.target.value))}
                            step="0.01"
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="ville" className="block text-sm font-medium text-gray-700">City</label>
                        <select
                            id="ville"
                            value={villeId.toString()}// Remove the unintended period here
                            onChange={e => {
                                const villeId = Number(e.target.value);
                                const ville = villes.find(v => v.id === villeId) || null;
                                setSelectedVille(ville);
                                setVilleId(villeId); // Optionally update the villeId state if needed
                            }}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        >
                            <option value="">Select City</option>
                            {villes.map(ville => (
                                <option key={ville.id} value={ville.id}>
                                    {ville.nom}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="flex justify-end space-x-4">
                        <button
                            type="button"
                            onClick={onClose}
                            className="py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-gray-700 bg-gray-300 hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
                        >
                        Close
                        </button>
                        <button
                            type="submit"
                            className="py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                        >
                            Save Changes
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditActiviteForm;
