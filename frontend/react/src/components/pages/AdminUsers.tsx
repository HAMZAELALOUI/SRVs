import React, { useEffect, useState } from "react";
import FlightsTable from "../molecules/FlightsTable";
import AddFlightForm from "../popups/AddFlightForm.tsx"; // Import your form
import volService from "../../../services/VolService";
import { Vol } from "../../../services/types";
import EditFlightForm from "../popups/EditFlightForm.tsx";
import UserAdminTable from "../molecules/UserAdminTable.tsx";

const AdminUsers: React.FC = () => {
  const [vols, setVols] = useState<Vol[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [isEditPopupVisible, setIsEditPopupVisible] = useState(false);
  const [selectedVolForEdit, setSelectedVolForEdit] = useState<Vol | null>(
    null
  );
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleEditClick = (vol: Vol) => {
    setSelectedVolForEdit(vol);
    setIsEditPopupVisible(true);
  };

  const handleUpdateVol = async (id: number, volDetails: Vol) => {
    setIsSubmitting(true); // Disable the form or button to prevent multiple submissions
    try {
      // Call the update API and wait for the response
      const response = await volService.updateVol(id, volDetails);

      // Assuming the server returns the updated Vol object
      if (response.status === 200) {
        // Update local state to reflect the change
        setVols((prevVols) =>
          prevVols.map((vol) => {
            if (vol.idVol === id) {
              // Spread the updated data into the current vol
              return { ...vol, ...response.data };
            }
            return vol;
          })
        );

        setIsEditPopupVisible(false); // Close the edit popup
        setSelectedVolForEdit(null); // Clear the currently selected vol for edit
      } else {
        // Handle situations where the server response might not be as expected
        console.error(
          "Failed to update the vol, server responded with status:",
          response.status
        );
      }
    } catch (error) {
      console.error("Error updating vol:", error);
      // Optionally, handle more user-friendly error reporting here
    } finally {
      setIsSubmitting(false); // Re-enable the form or button
    }
  };

  useEffect(() => {
    const fetchVols = async () => {
      try {
        const response = await volService.getAllVols();
        setVols(response.data); // Set the state with the fetched vols
      } catch (error) {
        console.error("Error fetching vols:", error);
      }
    };

    // Fetch the data only if the modal is not open
    if (!showModal) {
      fetchVols();
    }
  }, [showModal]);

  // Function to handle the form submission

  const addButton = (
    <button
      className="mb-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
      onClick={() => setShowModal(true)}
    >
      contact user
    </button>
  );

  return (
    <>
      <UserAdminTable
        vols={vols}
        addButton={addButton}
        onEditClick={handleEditClick}
      />
      {isEditPopupVisible && selectedVolForEdit && (
        <EditFlightForm
          vol={selectedVolForEdit}
          onClose={() => setIsEditPopupVisible(false)}
          onSave={handleUpdateVol}
        />
      )}
    </>
  );
};

export default AdminUsers;
