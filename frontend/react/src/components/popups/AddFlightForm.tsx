import React, {useEffect,useState} from "react";
import { Vol, Ville } from "../../../services/types.ts";
import villeService from "../../../services/VilleService.ts";
import Swal from 'sweetalert2';
import volService from "../../../services/VolService.ts";



interface AddFlightFormProps {
    onSubmit: (vol: Vol) => void;
    onCancel: () => void;
}

const AddFlightForm: React.FC<AddFlightFormProps> = ({ onSubmit, onCancel }) => {
    const [origin, setOrigin] = useState<Ville | null>(null);
    const [destination, setDestination] = useState<Ville | null>(null);
    const [heureDepart, setHeureDepart] = useState('');
    const [heureArrivee, setHeureArrivee] = useState('');
    const [prix, setPrix] = useState('');
    const [placesDisponibles, setPlacesDisponibles] = useState('');
    const [imageUrl, setImageUrl] = useState<File | null>(null);
    const [villes, setVilles] = useState<Ville[]>([]);
    const [isSubmitting, setIsSubmitting] = useState(false); // State to track form submission status

    useEffect(() => {
        villeService.getAllVilles()
            .then(data => {
                if (data) {
                    setVilles(data);
                } else {
                    throw new Error('No data returned');
                }
            })
            .catch(error => {
                console.error('Error fetching cities:', error);
                setVilles([]); // Maintain an empty array if there's an error
            });
    }, []);


    const handleSelectChange = (event, setter) => {
        const id = parseInt(event.target.value, 10);
        const selectedVille = villes.find(ville => ville.id === id);
        setter(selectedVille);
    };

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files ? event.target.files[0] : null;
        setImageUrl(file);  // Make sure this state is used to hold the file, not the URL
    };


    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();

        if (origin && destination && !isSubmitting) {
            setIsSubmitting(true);
            const formData = new FormData();
            console.log("Origin ID:", origin?.id);  // Should log a number, not undefined
            console.log("Destination ID:", destination?.id);

            // Check if `origin` and `destination` have `id` property
            if (origin.id && destination.id) {
                formData.append("originId", origin.id.toString()); // Ensure ids are appended as strings
                formData.append("destinationId", destination.id.toString());
            }

            formData.append("heureDepart", new Date(heureDepart).toISOString().split('T')[0]); // This will only send the date part
            formData.append("heureArrivee", new Date(heureArrivee).toISOString().split('T')[0]); // This will only send the date part

            formData.append("prix", parseFloat(prix).toString());
            formData.append("placesDisponibles", parseInt(placesDisponibles, 10).toString());

            if (imageUrl) {
                formData.append("image", imageUrl);
            }

            try {
                // Use the save function from your service
                const response = await volService.save(formData); // Assuming volService is where your `save` method resides
                console.log('Save successful:', response);
                setIsSubmitting(false); // Reset submission state
                Swal.fire({
                    title: 'Success!',
                    text: 'Flight has been added successfully',
                    icon: 'success'
                });
            } catch (error) {
                console.error('Save failed:', error);
                setIsSubmitting(false); // Reset submission state
                Swal.fire({
                    title: 'Error!',
                    text: 'Failed to add the flight',
                    icon: 'error'
                });
            }
        }
    };


    return (
        <div className="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full">
            <div className="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
                <form className="mt-3 text-center" onSubmit={handleSubmit}>
                    {/* Replace these fields with the actual fields you need for a flight */}
                    <div>
                        <label htmlFor="origin" className="block text-gray-700 font-bold mb-2">
                            Origin
                        </label>
                        <select
                            id="origin"
                            value={origin ? origin.id.toString() : ''}
                            onChange={(e) => handleSelectChange(e, setOrigin)}
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        >
                            <option value="">Select Origin</option>
                            {villes.map(ville => (
                                <option key={ville.id} value={ville.id.toString()}>
                                    {ville.nom}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="destination" className="block text-gray-700 font-bold mb-2">
                            Destination
                        </label>
                        <select
                            id="destination"
                            value={destination ? destination.id.toString() : ''}
                            onChange={(e) => handleSelectChange(e, setDestination)}
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        >
                            <option value="">Select Destination</option>
                            {villes.map(ville => (
                                <option key={ville.id} value={ville.id.toString()}>
                                    {ville.nom}
                                </option>
                            ))}
                        </select>
                    </div>
                        <div>
                            <label htmlFor="heureDepart" className="block text-sm font-medium text-gray-700">Date de
                                Depart</label>
                            <input
                                type="date"
                                id="heureDepart"
                                value={heureDepart}
                                onChange={(e) => setHeureDepart(e.target.value)}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="heureArrivee" className="block text-sm font-medium text-gray-700">Date de
                                Retour</label>
                            <input
                                type="date"
                                id="heureArrivee"
                                value={heureArrivee}
                                onChange={(e) => setHeureArrivee(e.target.value)}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="prix" className="block text-sm font-medium text-gray-700">Price</label>
                            <input
                                type="number"
                                id="prix"
                                value={prix}
                                onChange={(e) => setPrix(e.target.value)}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                                placeholder="0.00"
                                min="0"
                                step="0.01"
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="placesDisponibles" className="block text-sm font-medium text-gray-700">Available
                                Seats</label>
                            <input
                                type="number"
                                id="placesDisponibles"
                                value={placesDisponibles}
                                onChange={(e) => setPlacesDisponibles(e.target.value)}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                                min="0"
                                required
                            />
                        </div>
                        <div>
                            <label htmlFor="imageUrl" className="block text-sm font-medium text-gray-700">Image URL</label>
                            <input
                                type="file"
                                id="image"
                                onChange={handleFileChange}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                            />
                        </div>
                    {/* ... more input fields for each property of Vol */}
                    <button type="submit"
                            className="mt-4 inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700">
                            Add Flight
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

export default AddFlightForm;
