import React, { useEffect, useState } from "react";
import HotelTable from "../molecules/HotelTable.tsx";
import AddHotelForm from "../popups/AddHotelForm.tsx"; // Import your form
import { hotelService } from "../../../services/HotelService";
import { Hotel } from "../../../services/types";
import EditHotelForm from '../popups/EditHotelForm.tsx';
import Swal from "sweetalert2";

const AdminHotels: React.FC = () => {
    const [hotels, setHotels] = useState<Hotel[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [isEditPopupVisible, setIsEditPopupVisible] = useState(false);
    const [selectedHotelForEdit, setSelectedHotelForEdit] = useState<Hotel | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleEditClick = (hotel: Hotel) => {
        setSelectedHotelForEdit(hotel);
        setIsEditPopupVisible(true);
    };

    const handleUpdateHotel = async (id: number, hotelData: Hotel) => {
        setIsSubmitting(true);
        try {
            const response = await hotelService.updateHotel(id, hotelData);
            if (response) {
                setHotels((prevHotels) => prevHotels.map(hotel => hotel.id === id ? { ...hotel, ...response } : hotel));
                setIsEditPopupVisible(false);
                setSelectedHotelForEdit(null);
                Swal.fire("Update Successful!", "The hotel has been updated successfully.", "success");
            } else {
                Swal.fire("Update Failed", "Failed to update the hotel due to server error.", "error");
            }
        } catch (error) {
            console.error("Error updating hotel:", error);
            Swal.fire("Error", "An error occurred while updating the hotel.", "error");
        } finally {
            setIsSubmitting(false);
        }
    };

    useEffect(() => {
        const fetchHotels = async () => {
            try {
                const response = await hotelService.getAllHotels();
                setHotels(response);
            } catch (error) {
                console.error("Error fetching hotels:", error);
            }
        };

        if (!showModal) {
            fetchHotels();
        }
    }, [showModal]);

    const handleAddHotel = async (hotelData: Hotel) => {
        setIsSubmitting(true);
        try {
            const response = await hotelService.saveHotel(hotelData);
            if (response) {
                setHotels(prevHotels => [...prevHotels, response]);
                setShowModal(false);
                Swal.fire("Success!", "Hotel has been added successfully", "success");
            } else {
                throw new Error(`Server responded with status: ${response}`);
            }
        } catch (error) {
            console.error("Error adding hotel:", error);
            Swal.fire("Error!", "Failed to add the hotel", "error");
        } finally {
            setIsSubmitting(false);
        }
    };

    const addButton = (
        <button
            className="mb-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            onClick={() => setShowModal(true)}
        >
            Add Hotel
        </button>
    );

    return (
        <>
            {showModal && (
                <AddHotelForm onSubmit={handleAddHotel} onCancel={() => setShowModal(false)}/>
            )}
            <HotelTable hotels={hotels} addButton={addButton} onEditClick={handleEditClick} />
            {isEditPopupVisible && selectedHotelForEdit && (
                <EditHotelForm
                    hotel={selectedHotelForEdit}
                    onClose={() => setIsEditPopupVisible(false)}
                    onSave={handleUpdateHotel}
                />
            )}
        </>
    );
};

export default AdminHotels;