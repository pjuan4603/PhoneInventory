class Validator {
    static validateColor(color) {
        const regexPattern = /\d/;
        return !regexPattern.test(color.trim());
    }

    static validatePrice(priceComp, price, priceMin, priceMax) {

        if (price !== '' || (priceMin !== '' && priceMax !== '')) {

            let regexPattern = /^[0-9]*(\.[0-9]{0,2})?$/;

            if (priceComp === 'between') {

                if (!regexPattern.test(priceMin) || !regexPattern.test(priceMax)) {
                    return false;
                }

                let priceMinF = parseFloat(priceMin);
                let priceMaxF = parseFloat(priceMax);

                return priceMaxF >= priceMinF && priceMinF >= 0 && priceMaxF >= 0;
            } else {

                if (!regexPattern.test(price)) {
                    return false;
                }

                return parseFloat(price) >= 0;
            }

        } else if ((priceMin !== '' || priceMax !== '')) {
            return false;
        }

        return true;
    }

    static validateStorage(storageComp, storage, storageMin, storageMax) {

        if (storage !== '' || (storageMin !== '' && storageMax !== '')) {

            let regexPattern = /^[0-9]*$/;

            if (storageComp === 'between') {
                if (!regexPattern.test(storageMin) || !regexPattern.test(storageMax)) {
                    return false;
				}
                let storageMinF = parseFloat(storageMin);
                let storageMaxF = parseFloat(storageMax);
                return storageMaxF >= storageMinF && storageMinF >= 0 && storageMaxF >= 0;
            } else {

                if (!regexPattern.test(storage)) {
                    return false;
                }

                return parseFloat(storage) >= 0;
            }
        } else if ((storageMin !== '' || storageMax !== '')) {
            return false;
		}

        return true;
    }
}

export default Validator;
