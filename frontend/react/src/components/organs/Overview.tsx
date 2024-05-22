import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { FaUser, FaPlane, FaDollarSign, FaTicketAlt } from 'react-icons/fa';
import InfoCard from './InfoCard';

// Register the chart components
ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// Define a more cohesive color palette
const colors = {
    purple: 'rgba(123, 31, 162, 0.6)',
    blue: 'rgba(30, 136, 229, 0.6)',
    green: 'rgba(56, 142, 60, 0.6)',
    yellow: 'rgba(253, 216, 53, 0.6)',
    orange: 'rgba(255, 87, 34, 0.6)',
    white: '#FFF'
};

const userData = {
    labels: ['Users', 'Flights'],
    datasets: [{
        label: 'Count',
        data: [1024, 320],
        backgroundColor: [colors.purple, colors.blue],
        borderColor: [colors.purple, colors.blue].map(color => color.replace('0.6', '1')),
        borderWidth: 2,
    }],
};

const reservationData = {
    labels: ['Today', 'This Week', 'This Month'],
    datasets: [{
        label: 'Reservations',
        data: [15, 75, 300],
        backgroundColor: [colors.green, colors.yellow, colors.orange],
        borderColor: [colors.green, colors.yellow, colors.orange].map(color => color.replace('0.6', '1')),
        borderWidth: 2,
    }],
};

const chartOptions = {
    responsive: true,
    scales: {
        y: { beginAtZero: true }
    },
    plugins: {
        legend: { position: 'top' },
        title: { display: true },
    },
};

const Overview = () => {
    return (
        <div className="flex-1 p-8 bg-gray-50 animate-fade-in-down">
            <h1 className="text-3xl font-bold text-gray-800 mb-6">Dashboard Overview</h1>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
                <InfoCard icon={<FaUser />} color="purple" title="Total Users" value="1,024" change="+10% from last week" />
                <InfoCard icon={<FaDollarSign />} color="blue" title="Revenue" value="$48,560" change="+5% from last week" />
                <InfoCard icon={<FaPlane />} color="green" title="Feedback" value="320" change="-3% from last week" />
                <InfoCard icon={<FaTicketAlt />} color="red" title="New Bookings" value="158" change="+20% from last week" />
                <div className="col-span-2 bg-white shadow-lg rounded-lg p-6">
                    <Bar data={userData} options={{ ...chartOptions, title: { text: 'User and Flight Count', display: true }}} />
                </div>
                <div className="col-span-2 bg-white shadow-lg rounded-lg p-6">
                    <Bar data={reservationData} options={{ ...chartOptions, title: { text: 'Reservation Trends', display: true }}} />
                </div>
            </div>
        </div>
    );
};



export default Overview;
