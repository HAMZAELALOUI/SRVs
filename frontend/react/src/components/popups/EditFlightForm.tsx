import React, { useEffect, useState } from "react";
import { Ville, Vol } from "../../../services/types";
import Swal from 'sweetalert2';
import villeService from "../../../services/VilleService.ts";

const EditFlightForm: React.FC<{
    vol: Vol;
    onClose: () => void;
    onSave: (id: number, formData: FormData) => Promise<void>;
}> = ({ vol, onClose, onSave }) => {
    const [originId, setOriginId] = useState<string>(vol.origin.id.toString());
    const [destinationId, setDestinationId] = useState<string>(vol.destination.id.toString());
    const [heureDepart, setHeureDepart] = useState<string>(vol.heureDepart);
    const [heureArrivee, setHeureArrivee] = useState<string>(vol.heureArrivee);
    const [price, setPrice] = useState<number>(vol.prix);
    const [placesDisponibles, setPlacesDisponibles] = useState<number>(vol.placesDisponibles);
    const [imageFile, setImageFile] = useState<File | null>(null);
    const [villes, setVilles] = useState<Ville[]>([]);

    useEffect(() => {
        villeService.getAllVilles()
            .then(data => setVilles(data))
            .catch(error => {
                console.error('Error fetching cities:', error);
                setVilles([]);
            });
    }, []);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files ? event.target.files[0] : null;
        setImageFile(file);
    };

        const handleSubmit = async (event: React.FormEvent) => {
            event.preventDefault();
            Swal.fire({
                title: "Do you want to save the changes?",
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: "Save",
                denyButtonText: "Don't save",
                icon: 'question'
            }).then(async (result) => {
                if (result.isConfirmed) {
                    const formData = new FormData();
                    formData.append('originId', originId);
                    formData.append('destinationId', destinationId);
                    formData.append("heureDepart", new Date(heureDepart).toISOString().split('T')[0]);
                    formData.append("heureArrivee", new Date(heureArrivee).toISOString().split('T')[0]);
                    formData.append('prix', price.toString());
                    formData.append('placesDisponibles', placesDisponibles.toString());
                    if (imageFile) {
                        formData.append('image', imageFile);
                    }

                    await onSave(vol.idVol, formData);
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
                        <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">Image URL</label>
                        <img
                            src={`http://localhost:8080${vol.imageUrl}`}
                            alt="Vol Image"
                            className="rounded-full w-20 h-20 object-cover border-2 border-gray-300 shadow-sm"
                        />

                    </div>
                    <div>
                        <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">Edit the
                            image</label>
                        <input
                            type="file"
                            id="imageUrl"

                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="origin" className="block text-sm font-medium text-gray-700">Origin</label>
                        <select
                            id="origin"
                            value={originId}
                            onChange={e => setOriginId(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        >
                            {villes.map(ville => (
                                <option key={ville.id} value={ville.id.toString()}>
                                    {ville.nom}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="destination"
                               className="block text-sm font-medium text-gray-700">Destination</label>
                        <select
                            id="destination"
                            value={destinationId}
                            onChange={e => setDestinationId(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        >
                            {villes.map(ville => (
                                <option key={ville.id} value={ville.id.toString()}>
                                    {ville.nom}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="heureDepart" className="block text-sm font-medium text-gray-700">Departure
                            Date</label>
                        <input
                            type="date"
                            id="heureDepart"
                            value={heureDepart}
                            onChange={(e) => setHeureDepart(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="heureArrivee" className="block text-sm font-medium text-gray-700">Arrival
                            Date</label>
                        <input
                            type="date"
                            id="heureArrivee"
                            value={heureArrivee}
                            onChange={(e) => setHeureArrivee(e.target.value)}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="price" className="block text-sm font-medium text-gray-700">Price</label>
                        <input
                            type="number"
                            id="price"
                            value={price}
                            onChange={(e) => setPrice(Number(e.target.value))}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
                    </div>
                    <div>
                        <label htmlFor="placesDisponibles" className="block text-sm font-medium text-gray-700">Available
                            Seats</label>
                        <input
                            type="number"
                            id="placesDisponibles"
                            value={placesDisponibles}
                            onChange={(e) => setPlacesDisponibles(Number(e.target.value))}
                            className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                        />
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

export default EditFlightForm;
