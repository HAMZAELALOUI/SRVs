import React, { useEffect, useState } from "react";
import UserAdminTable from "../molecules/UserAdminTable.tsx";
import {Utilisateur} from "../../../services/types.ts";
import UtilisateurServices from "../../../services/UtilisateurServices";

const AdminUsers: React.FC = () => {
  const [utilisateurs, setUtilisateurs] = useState<Utilisateur[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [isEditPopupVisible, setIsEditPopupVisible] = useState(false);
  const [selectedUserForEdit, setSelectedUserForEdit] = useState<Utilisateur | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleEditClick = (user: Utilisateur) => {
    setSelectedUserForEdit(user);
    setIsEditPopupVisible(true);
  };

  const handleUpdateUser = async (id: number, userDetails: Utilisateur) => {
    setIsSubmitting(true);
    try {
      const response = await UtilisateurServices.updateUser(id, userDetails);
      if (response.status === 200) {
        setUtilisateurs((prevUsers) =>
            prevUsers.map((user) => {
              if (user.id === id) {
                return { ...user, ...response.data };
              }
              return user;
            })
        );
        setIsEditPopupVisible(false);
        setSelectedUserForEdit(null);
      } else {
        console.error("Failed to update the user, server responded with status:", response.status);
      }
    } catch (error) {
      console.error("Error updating user:", error);
    } finally {
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const users = await UtilisateurServices.getAllUsers();
        setUtilisateurs(users);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    if (!showModal) {
      fetchUsers();
    }
  }, [showModal]);

  const addButton = (
      <button
          className="mb-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          onClick={() => setShowModal(true)}
      >
        Add User
      </button>
  );

  return (
      <>
        <UserAdminTable
            users={utilisateurs}
            addButton={addButton}
            onEditClick={handleEditClick}
        />

      </>
  );
};

export default AdminUsers;