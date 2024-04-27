import { useLocation } from 'react-router-dom';
import { Vol } from "../../../services/types";

const FlightDetails: React.FC = () => {
    const location = useLocation();
    const { vol } = location.state as { vol: Vol };

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
                        className="bg-color1 border border-transparent rounded-md py-2 px-4 flex items-center justify-center text-sm font-medium text-white hover:bg-color2 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-color2">
                        Start Booking
                    </button>

                </div>
            </div>
        </div>
    );
};

export default FlightDetails;
