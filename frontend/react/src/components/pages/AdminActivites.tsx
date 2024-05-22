import React, { useEffect, useState } from "react";
import ActiviteTable from "../molecules/ActiviteTable";
import { activiteService } from "../../../services/ActiviteService";
import { Activite } from "../../../services/types";
import AddActiviteForm from "../popups/AddActiviteForm";
import EditActiviteForm from "../popups/EditActiviteForm";
import Swal from "sweetalert2";

const AdminActivites: React.FC = () => {
    const [activites, setActivites] = useState<Activite[]>([]);
    const [showModal, setShowModal] = useState(false);
    const [isEditPopupVisible, setIsEditPopupVisible] = useState(false);
    const [selectedActiviteForEdit, setSelectedActiviteForEdit] = useState<Activite | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleEditClick = (activite: Activite) => {
        setSelectedActiviteForEdit(activite);
        setIsEditPopupVisible(true);
    };

    const handleUpdateActivite = async (id: number, activiteData: Activite) => {
        setIsSubmitting(true);
        try {
            const response = await activiteService.updateActivite(id, activiteData);
            if (response) {
                setActivites((prevActivites) =>
                    prevActivites.map((activite) => (activite.id === id ? { ...activite, ...response } : activite))
                );
                setIsEditPopupVisible(false);
                setSelectedActiviteForEdit(null);
                Swal.fire("Update Successful!", "The activite has been updated successfully.", "success");
            } else {
                Swal.fire("Update Failed", "Failed to update the activite due to server error.", "error");
            }
        } catch (error) {
            console.error("Error updating activite:", error);
            Swal.fire("Error", "An error occurred while updating the activite.", "error");
        } finally {
            setIsSubmitting(false);
        }
    };

    useEffect(() => {
        const fetchActivites = async () => {
            try {
                const response = await activiteService.findAll();
                setActivites(response);
            } catch (error) {
                console.error("Error fetching activites:", error);
            }
        };

        if (!showModal) {
            fetchActivites();
        }
    }, [showModal]);

    const handleAddActivite = async (activiteData: Activite) => {
        setIsSubmitting(true);
        try {
            const response = await activiteService.saveActivite(activiteData);
            if (response) {
                setActivites((prevActivites) => [...prevActivites, response]);
                setShowModal(false);
                Swal.fire("Success!", "Activite has been added successfully", "success");
            } else {
                throw new Error(`Server responded with status: ${response}`);
            }
        } catch (error) {
            console.error("Error adding activite:", error);
            Swal.fire("Error!", "Failed to add the activite", "error");
        } finally {
            setIsSubmitting(false);
        }
    };

    const addButton = (
        <button
            className="mb-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            onClick={() => setShowModal(true)}
        >
            Add Activite
        </button>
    );

    return (
        <>
            {showModal && <AddActiviteForm onSubmit={handleAddActivite} onCancel={() => setShowModal(false)} />}
            <ActiviteTable activites={activites} addButton={addButton} onEditClick={handleEditClick} />
            {isEditPopupVisible && selectedActiviteForEdit && (
                <EditActiviteForm
                    activite={selectedActiviteForEdit}
                    onClose={() => setIsEditPopupVisible(false)}
                    onSave={handleUpdateActivite}
                />
            )}
        </>
    );
};

export default AdminActivites;
