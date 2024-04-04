import React, { useState, useEffect } from 'react';
import DataTable from 'react-data-table-component';
import axios from "axios";
import "./PhoneTable.css";

const baseURL = "http://localhost:8080/phones";

export default function PhoneTable({ queryString }) {
    const [data, setData] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const url = baseURL + "?" + queryString;
                console.log('Query:', queryString);
                console.log(url);
                const response = await axios.get(url);
                setData(response.data);
                setError(null);
            } catch (error) {
                setData(null);
                console.error('Error fetching data:', error);
                setError('Invalid input, please check storage and price inputs.');
            }
        };

        if (queryString) {
            fetchData();
        }
    }, [queryString]);

    const columns = [
        {
            name: "Brand",
            selector: (row) => row.brand,
            sortable: true
        },
        {
            name: "Model",
            selector: (row) => row.model,
            sortable: true
        },
        {
            name: "Storage (GB)",
            selector: (row) => row.storage,
            sortable: true
        },
        {
            name: "Color",
            selector: (row) => row.color,
            sortable: true
        },
        {
            name: "Price",
            selector: (row) => row.price,
            sortable: true,
            cell: row => `$${row.price}`
        },
    ];

    const paginationOptions = {
        rowsPerPageText: 'Rows per page:',
        rangeSeparatorText: 'of',
        selectAllRowsItem: true,
        selectAllRowsItemText: 'All'
    };

    return (

        <div class="container mt-10 margin-top">
            {error && (
                <div className="alert alert-danger" role="alert">
                    {error}
                </div>
            )}
            {data && (
                <DataTable
                    columns={columns}
                    data={data}
                    fixedHeader
                    pagination
                    paginationPerPage={10}
                    paginationRowsPerPageOptions={[5, 10, 15, 20]}
                    paginationComponentOptions={paginationOptions}
                />
            )}
        </div>

    );
}


