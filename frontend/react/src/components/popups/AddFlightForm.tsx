import React, {useEffect,useState} from "react";
import { Vol, Ville } from "../../../services/types.ts";
import villeService from "../../../services/VilleService.ts";
import Swal from 'sweetalert2';



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
    const [imageUrl, setImageUrl] = useState('');
    const [villes, setVilles] = useState<Ville[]>([]);
    const [isSubmitting, setIsSubmitting] = useState(false); // State to track form submission status

    useEffect(() => {
        villeService.getAllVilles()
            .then(response => setVilles(response.data))
            .catch(error => console.error('Error fetching cities:', error));
    }, []);


    const handleSelectChange = (event: React.ChangeEvent<HTMLSelectElement>, setter: React.Dispatch<React.SetStateAction<Ville | null>>) => {
        const selectedId = parseInt(event.target.value, 10);
        const selectedVille = villes.find(ville => ville.id === selectedId);
        setter(selectedVille || null);
    };


    const handleSubmit = async (event: React.FormEvent) => {

        if (origin && destination && !isSubmitting) {
            setIsSubmitting(true);
            const volToSubmit: Vol = {
                origin,
                destination,
                heureDepart: new Date(heureDepart),
                heureArrivee: new Date(heureArrivee),
                prix: parseFloat(prix),
                placesDisponibles: parseInt(placesDisponibles, 10),
                imageUrl
            };
            onSubmit(volToSubmit)
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
                                type="url"
                                id="imageUrl"
                                value={imageUrl}
                                onChange={(e) => setImageUrl(e.target.value)}
                                className="mt-1 block w-full border border-gray-300 rounded-md shadow-sm"
                                placeholder="http://"
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
