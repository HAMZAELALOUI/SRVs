const InfoCard = ({ icon, color, title, value, change }) => (
    <div className={`transform hover:scale-105 transition duration-500 ease-in-out shadow-lg rounded-lg p-6 flex items-center justify-between text-white
        ${color === 'purple' ? 'bg-gradient-to-tr from-purple-500 to-purple-300' :
         color === 'blue' ? 'bg-gradient-to-tr from-blue-500 to-blue-300' :
         color === 'green' ? 'bg-gradient-to-tr from-green-500 to-green-300' :
         color === 'yellow' ? 'bg-gradient-to-tr from-yellow-500 to-yellow-300' :
         'bg-gradient-to-tr from-orange-500 to-orange-300'}`}
    >
        <div>
            <h2 className="text-xl font-semibold">{title}</h2>
            <p className="text-3xl font-bold">{value}</p>
            <p className={`text-sm ${change.startsWith('+') ? 'text-green-200' : 'text-red-200'}`}>{change}</p>
        </div>
        {icon}
    </div>
);

export default InfoCard;