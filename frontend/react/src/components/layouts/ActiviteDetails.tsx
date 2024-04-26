import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { activiteService } from "../../../services/ActiviteService";
import { Activite, Ville } from "../../../services/types";

const ActiviteDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>(); // Récupérer l'ID de l'activité depuis l'URL
  const [activite, setActivite] = useState<Activite | null>(null);
  const [ville, setVille] = useState<Ville | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const activiteData = await activiteService.findActiviteWithVilleById(Number(id));
        console.log('activiteData:', activiteData);
        setActivite(activiteData);

        // Vérifie si activiteData.ville est un objet avec les propriétés 'nom' et 'pays'
        if (activiteData.ville && typeof activiteData.ville === 'object') {
          const { nom, pays } = activiteData.ville;
          console.log('nom:', nom);
          console.log('pays:', pays);
          setVille({ nom, pays });
        } else {
          console.error('Ville data is not an object or is undefined:', activiteData.ville);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, [id]); // Utilisez l'ID extrait comme dépendance

  if (!activite || !ville) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>{activite.nom}</h2>
      <img src={activite.image} alt={activite.nom} />
      <p>Lieu: {activite.lieu}</p>
      <p>Description: {activite.description}</p>
      <p>Horaire: {activite.horaire}</p>
      <p>Prix: {activite.prix}</p>

      <h3>Ville: {ville.nom}</h3>
      <p>Pays: {ville.pays}</p>
    </div>
  );
};

export default ActiviteDetails;
