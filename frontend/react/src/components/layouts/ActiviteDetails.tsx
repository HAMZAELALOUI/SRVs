// Importez React et les hooks nécessaires
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; 
import { Activite } from "../../../services/types";
// Importez useParams pour récupérer les paramètres d'URL

// Définissez votre composant pour afficher les détails de l'activité
const ActiviteDetails: React.FC = () => {
  // Utilisez useParams pour récupérer l'identifiant de l'activité depuis l'URL
  const { id } = useParams<{ id: string }>();

  // Utilisez useState pour stocker les détails de l'activité
  const [activite, setActivite] = useState<Activite | null>(null);

  useEffect(() => {
    // Utilisez une fonction pour récupérer les détails de l'activité en fonction de son identifiant
    const fetchActiviteDetails = async () => {
      try {
        // Effectuez une requête API pour récupérer les détails de l'activité
        // Remplacez cet exemple par votre propre logique pour récupérer les détails de l'activité en fonction de son identifiant
        const response = await fetch(`http://example.com/api/activites/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch activite details');
        }
        const data = await response.json();
        // Mettez à jour l'état avec les détails de l'activité récupérés
        setActivite(data);
      } catch (error) {
        console.error('Error fetching activite details:', error);
      }
    };

    // Appelez la fonction pour récupérer les détails de l'activité lorsque le composant est monté
    fetchActiviteDetails();
  }, [id]); // Utilisez l'identifiant de l'activité comme dépendance

  // Si les détails de l'activité ne sont pas encore chargés, affichez un message de chargement
  if (!activite) {
    return <div>Loading...</div>;
  }

  // Une fois les détails de l'activité chargés, affichez-les
  return (
    <div>
      <h1>Activite Details</h1>
      <p>Nom: {activite.nom}</p>
      <p>Description: {activite.description}</p>
      {/* Ajoutez d'autres détails de l'activité selon vos besoins */}
    </div>
  );
};

export default ActiviteDetails;
