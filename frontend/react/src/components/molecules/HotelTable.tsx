import React from "react";
import Swal from "sweetalert2";
import { hotelService } from "../../../services/HotelService";
import { Hotel } from "../../../services/types";

interface HotelTableProps {
  hotels: Hotel[];
  addButton: React.ReactNode;
  onEditClick: (hotel: Hotel) => void;
}

const HotelTable: React.FC<HotelTableProps> = ({
  hotels,
  addButton,
  onEditClick,
}) => {
  return (
    <div className="container mx-auto mt-5 p-6 bg-white rounded shadow-md">
      <h1 className="text-xl font-semibold mb-5">All Hotels</h1>
      {addButton}
      <table className="min-w-full table-auto">
        <thead className="bg-gray-800 text-white">
          <tr>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Name
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Location
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Image
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Stars
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Room Price
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm text-left">
              Description
            </th>
            <th className="py-3 px-4 uppercase font-semibold text-sm">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="text-gray-700">
          {hotels &&
            hotels.map((hotel) => (
              <tr key={hotel.id} className="hover:bg-gray-100">
                <td className="py-3 px-4">{hotel.nom}</td>
                <td className="py-3 px-4">{hotel.emplacement}</td>
                <td className="py-3 px-4">
                  <img
                    src={hotel.image}
                    alt={hotel.nom}
                    width="100"
                    height="100"
                  />
                </td>
                <td className="py-3 px-4">{hotel.nombreEtoiles} stars</td>
                <td className="py-3 px-4">${hotel.prixChambres.toFixed(2)}</td>
                <td className="py-3 px-4">{hotel.description}</td>
                <td className="py-3 px-4 text-center">
                  <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      onEditClick(hotel);
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
                          hotelService
                            .deleteHotel(hotel.id)
                            .then(() => {
                              Swal.fire(
                                "Deleted!",
                                "The hotel has been deleted.",
                                "success"
                              );
                              window.location.reload();
                            })
                            .catch((error) => {
                              console.error("Failed to delete hotel:", error);
                              Swal.fire(
                                "Failed!",
                                "There was a problem deleting the hotel.",
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

export default HotelTable;
