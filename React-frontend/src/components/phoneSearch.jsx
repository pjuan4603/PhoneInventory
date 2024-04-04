import React, { useState, useEffect } from 'react';
import PhoneTable from "./Table/PhoneTable.jsx"
import FilterCriteria from "./Filter/FilterInput.jsx"

export default function PhoneSearch() {
    const [queryString, setQueryString] = useState('');

    const handleSearch = (newQueryString) => {
        setQueryString(newQueryString);
    };

    return (
        <div>
            <h1>Phone Inventory</h1>
            <FilterCriteria onSearch={handleSearch} />
            <PhoneTable queryString={queryString} />
        </div>
    );
}