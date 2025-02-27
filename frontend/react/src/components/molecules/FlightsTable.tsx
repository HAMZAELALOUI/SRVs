// src/components/FlightsTable.tsx
import React, {ReactNode} from "react";
import { Vol } from "../../../services/types.ts";
import volService from "../../../services/VolService.ts";
import Swal from 'sweetalert2';


interface FlightsTableProps {
  vols: Vol[];
  addButton: ReactNode;
  onEditClick: (vol: Vol) => void;
}


const FlightsTable: React.FC<FlightsTableProps> = ({ vols, addButton, onEditClick }) => {



  return (
    <div className="container mx-auto mt-5 p-6 bg-white rounded shadow-md">
      <h1 className="text-xl font-semibold mb-5">All Flights</h1>
      {addButton}
      <table className="min-w-full table-auto">
        <thead className="bg-gray-800 text-white">
          <tr>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Origin</th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Destination</th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Date de Depart</th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Date de Retour </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Prix </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">Place Disponible </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm">Actions</th>
          </tr>
        </thead>
        <tbody className="text-gray-700">
        {vols.map((vol) => (
            <tr key={vol.idVol} className="hover:bg-gray-100">
              <td className="py-3 px-4">{vol.origin.nom}</td>
              <td className="py-3 px-4">{vol.destination.nom}</td>
              <td className="py-3 px-4">{vol.heureDepart.toString()}</td>
              <td className="py-3 px-4">{vol.heureArrivee.toString()}</td>
              <td className="py-3 px-4">${vol.prix.toFixed(2)}</td>
              <td className="py-3 px-4">{vol.placesDisponibles}</td>
              <td className="py-3 px-4 text-center">
                <a href="#" onClick={(e) => {
                  e.preventDefault();
                  onEditClick(vol);
                }} className="text-indigo-600 hover:text-indigo-900">
                  Edit
                </a>
                <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      Swal.fire({
                        title: 'Are you sure?',
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes, delete it!'
                      }).then((result) => {
                        if (result.isConfirmed) {
                          volService.deleteVol(vol.idVol)
                              .then(() => {
                                Swal.fire(
                                    'Deleted!',
                                    'Your flight has been deleted.',
                                    'success'
                                );
                                window.location.reload();
                              })
                              .catch((error) => {
                                console.error("Failed to delete vol:", error);
                                Swal.fire(
                                    'Failed!',
                                    'There was a problem deleting your flight.',
                                    'error'
                                );
                              });
                        }
                      });
                    }}
                    className="text-red-600 hover:text-red-900 ml-10"
                >
                  Delete
                </a>
              </td>
            </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
};

export default FlightsTable;
