import React, { useState, useEffect } from 'react';

export default function FilterCriteria({ onSearch }) {
    const [brand, setBrand] = useState('');
    const [model, setModel] = useState('');
    const [priceComp, setPriceComp] = useState('equal');
    const [price, setPrice] = useState('');
    const [priceMin, setPriceMin] = useState('');
    const [priceMax, setPriceMax] = useState('');
    const [storageComp, setStorageComp] = useState('equal');
    const [storage, setStorage] = useState('');
    const [storageMin, setStorageMin] = useState('');
    const [storageMax, setStorageMax] = useState('');
    const [color, setColor] = useState('');

    useEffect(() => {
        setPrice('');
        setPriceMin('');
        setPriceMax('');    
    }, [priceComp]);

    useEffect(() => {
        setStorage('');
        setStorageMin('');
        setStorageMax('');
    }, [storageComp]);

    const handleSearch = () => {
        const queryParams = {
            brand,
            model,
            priceComp: (priceMin === '' && priceMax === '' && price === '') ? '' : priceComp,
            price,
            priceMin,
            priceMax,
            storageComp: (storageMin === '' && storageMax === '' && storage === '') ? '' : storageComp,
            storage,
            storageMin,
            storageMax,
            color
        };

        let queryString = Object.keys(queryParams)
            .map(key => {
                if (queryParams[key] !== '') {
                    return `${key}=${encodeURIComponent(queryParams[key])}`;
                }
                return null;
            })
            .filter(Boolean)
            .join('&');

        if (queryString === "") {
            console.log("**")
            queryString = "empty"
        }
        onSearch(queryString);
    };

    return (
        <div className="container mt-5">
            <h2 class="display-6 text-center">Filter Criteria</h2>
            <div className="row">
                <div className="col-md-4">
                    <label htmlFor="brand">Brand</label>
                    <input type="text" id="brand" name="brand" className="form-control" value={brand} onChange={e => setBrand(e.target.value)} />
                </div>
                <div className="col-md-4">
                    <label htmlFor="model">Model</label>
                    <input type="text" id="model" name="model" className="form-control" value={model} onChange={e => setModel(e.target.value)} />
                </div>

                <div className="col-md-4">
                    <label htmlFor="color">Color</label>
                    <input type="text" id="color" name="color" className="form-control" value={color} onChange={e => setColor(e.target.value)} />
                </div>
                
            </div>
            <div className="row">
                <div className="col-md-4">
                    <label htmlFor="storage">Storage</label>
                    <select className="form-control" value={storageComp} onChange={e => setStorageComp(e.target.value)}>
                        <option value="between">Between</option>
                        <option value="gte">Greater Than or Equal to</option>
                        <option value="equal">Equal to</option>
                        <option value="lte">Less than or Equal to</option>
                    </select>
                    {storageComp === 'between' && (
                        <>
                            <input type="text" id="storageMin" name="storageMin" className="form-control" placeholder="Min" value={storageMin} onChange={e => setStorageMin(e.target.value)} />
                            <input type="text" id="storageMax" name="storageMax" className="form-control" placeholder="Max" value={storageMax} onChange={e => setStorageMax(e.target.value)} />
                        </>
                    )}
                    {['gte', 'equal', 'lte'].includes(storageComp) && (
                        <input type="text" id="storage" name="storage" className="form-control" value={storage} onChange={e => setStorage(e.target.value)} />
                    )}
                </div>

                <div className="col-md-4">
                    <label htmlFor="price">Price</label>
                    <select className="form-control" value={priceComp} onChange={e => setPriceComp(e.target.value)}>
                        <option value="between">Between</option>
                        <option value="gte">Greater than or Equal to</option>
                        <option value="equal">Equal to</option>
                        <option value="lte">Less than or Equal to</option>
                    </select>
                    {priceComp === 'between' && (
                        <>
                            <input type="text" id="priceMin" name="priceMin" className="form-control" placeholder="Min" value={priceMin} onChange={e => setPriceMin(e.target.value)} />
                            <input type="text" id="priceMax" name="priceMax" className="form-control" placeholder="Max" value={priceMax} onChange={e => setPriceMax(e.target.value)} />
                        </>
                    )}
                    {['gte', 'equal', 'lte'].includes(priceComp) && (
                        <input type="text" id="price" name="price" className="form-control" value={price} onChange={e => setPrice(e.target.value)} />
                    )}
                </div>
                
            </div>

            <div>
                <button className="btn btn-primary mt-3" onClick={handleSearch}>Search</button>
            </div>
        </div>
    );
}
