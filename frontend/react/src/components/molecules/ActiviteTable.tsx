import React from "react";
import { Activite } from "../../../services/types"; // Assuming Activite type is defined here
import Swal from "sweetalert2";
import { activiteService } from "../../../services/ActiviteService";

interface ActiviteTableProps {
  activites: Activite[];
  addButton: React.ReactNode;
  onEditClick: (activite: Activite) => void;
}

const ActiviteTable: React.FC<ActiviteTableProps> = ({
  activites,
  addButton,
  onEditClick,
}) => {
  return (
    <div className="container mx-auto mt-5 p-6 bg-white rounded shadow-md">
      <h1 className="text-xl font-semibold mb-5">All Activities</h1>
      {addButton}
      <table className="min-w-full table-auto">
        <thead className="bg-gray-800 text-white">
          <tr>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Name
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              City
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Image
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Place
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Description
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Schedule
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Price
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="text-gray-700">
          {activites &&
            activites.map((activite) => (
              <tr key={activite.id} className="hover:bg-gray-100">
                <td className="py-3 px-4">{activite.nom}</td>
                <td className="py-3 px-4">{activite.ville.nom}</td>
                <td className="py-3 px-4">

                  <img
                    src={activite.image}
                    alt={activite.nom}
                    width="100"
                    height="100"
                  />{" "}
                </td>
                <td className="py-3 px-4">{activite.lieu}</td>
                <td className="py-3 px-4">{activite.description}</td>
                <td className="py-3 px-4">{activite.horaire}</td>
                <td className="py-3 px-4">${activite.prix.toFixed(2)}</td>
                <td className="py-3 px-4 text-center">
                  <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      onEditClick(activite);
                    }}
                    className="text-indigo-600 hover:text-indigo-900"
                  >
                    Edit
                  </a>
                  <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      Swal.fire({
                        title: "Are you sure?",
                        text: "You won't be able to revert this!",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "Yes, delete it!",
                      }).then((result) => {
                        if (result.isConfirmed) {
                          activiteService
                            .deleteById(activite.id)
                            .then(() => {
                              Swal.fire(
                                "Deleted!",
                                "Your flight has been deleted.",
                                "success"
                              );
                              window.location.reload();
                            })
                            .catch((error) => {
                              console.error("Failed to delete vol:", error);
                              Swal.fire(
                                "Failed!",
                                "There was a problem deleting your flight.",
                                "error"
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

export default ActiviteTable;
