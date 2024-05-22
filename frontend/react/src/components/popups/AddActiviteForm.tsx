import React, { useEffect, useState } from "react";
import { Activite, Ville } from "../../../services/types";
import villeService from "../../../services/VilleService";
import Swal from 'sweetalert2';

interface AddActiviteFormProps {
    onSubmit: (activite: Activite) => void;
    onCancel: () => void;
}

const AddActiviteForm: React.FC<AddActiviteFormProps> = ({ onSubmit, onCancel }) => {
    const [nom, setNom] = useState('');
    const [lieu, setLieu] = useState('');
    const [description, setDescription] = useState('');
    const [horaire, setHoraire] = useState('');
    const [prix, setPrix] = useState('');
    const [image, setImage] = useState('');
    const [villeId, setVilleId] = useState<number | null>(null);
    const [villes, setVilles] = useState<Ville[]>([]);
    const [isSubmitting, setIsSubmitting] = useState(false);
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
        if (!isSubmitting) {
            setIsSubmitting(true);
            const activiteData = {
                nom,
                lieu,
                description,
                horaire,
                prix: parseFloat(prix),
                image,
                ville: selectedVille // Send the selected Ville object
            };

            try {
                await onSubmit(activiteData); // Pass the activiteData object to the onSubmit function
                setIsSubmitting(false);
                Swal.fire({
                    title: 'Success!',
                    text: 'Activite has been added successfully',
                    icon: 'success'
                });
            } catch (error) {
                console.error('Save failed:', error);
                setIsSubmitting(false);
                Swal.fire({
                    title: 'Error!',
                    text: 'Failed to add the activite',
                    icon: 'error'
                });
            }
        }
    };

    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full">
            <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
                <form className="mt-3 text-center" onSubmit={handleSubmit}>
                    {/* Fields for activite data */}
                    <input
                        type="text"
                        placeholder="Activity Name"
                        value={nom}
                        onChange={(e) => setNom(e.target.value)}
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <input
                        type="text"
                        placeholder="Location"
                        value={lieu}
                        onChange={(e) => setLieu(e.target.value)}
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <textarea
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <input
                        type="text"
                        placeholder="Schedule"
                        value={horaire}
                        onChange={(e) => setHoraire(e.target.value)}
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <input
                        type="number"
                        placeholder="Price"
                        value={prix}
                        onChange={(e) => setPrix(e.target.value)}
                        step="0.01"
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <input
                        type="text"
                        placeholder="Image URL"
                        value={image}
                        onChange={(e) => setImage(e.target.value)}
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    />
                    <select
                        value={villeId ? villeId.toString() : ''}
                        onChange={e => {
                            const villeId = Number(e.target.value);
                            const ville = villes.find(v => v.id === villeId) || null;
                            setSelectedVille(ville);
                            setVilleId(villeId); // Optionally update the villeId state if needed
                        }}
                        required
                        className="mt-5 block w-full border border-gray-300 rounded-md shadow-sm"
                    >
                        <option value="">Select City</option>
                        {villes.map(ville => (
                            <option key={ville.id} value={ville.id}>
                                {ville.nom}
                            </option>
                        ))}
                    </select>
                    <button type="submit"
                            className="mt-4 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700">
                        Add Activity
                    </button>

                    <button type="button" onClick={onCancel}
                            className="mt-4 ml-4 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-red-600 hover:bg-red-700">
                        Cancel
                    </button>
                </form>
            </div>
        </div>
    );
};

export default AddActiviteForm;
