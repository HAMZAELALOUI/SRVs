import CustomersTable from "../molecules/CustomersTable.tsx";


const Overview= () => {

    return (

    <div className="flex-1 p-8 bg-gray-100">
        <h1 className="text-3xl font-bold text-gray-800 mb-4">Overview</h1>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {/* Widgets */}
            <div className="bg-white shadow-md rounded-lg p-6">
                <h2 className="text-xl font-semibold text-gray-700">Total Users</h2>
                <p className="text-3xl font-bold">1,024</p>
                <p className="text-green-500">+10% from last week</p>
            </div>
            <div className="bg-white shadow-md rounded-lg p-6">
                <h2 className="text-xl font-semibold text-gray-700">Revenue</h2>
                <p className="text-3xl font-bold">$48,560</p>
                <p className="text-green-500">+5% from last week</p>
            </div>
            <div className="bg-white shadow-md rounded-lg p-6">
                <h2 className="text-xl font-semibold text-gray-700">Feedback</h2>
                <p className="text-3xl font-bold">320</p>
                <p className="text-red-500">-3% from last week</p>
            </div>
        </div>
        <div className="flex-1 p-8 bg-gray-100">
            <h1 className="text-3xl font-bold text-gray-800 mb-4">Overview</h1>
            <CustomersTable />
        </div>
    </div>
    );
};

export default Overview;