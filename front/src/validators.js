
export function validatePositiveNumber(number) {
    return !(number === "") && !isNaN(parseInt(number)) && isFinite(number) && parseInt(number) > 0
}

export function validateNumber(number) {
    return !(number === "") && !isNaN(parseInt(number)) && isFinite(number)
}

export function validateFloatNumber(number) {
    return !(number === "") && !isNaN(parseFloat(number)) && isFinite(number)
}


export function validateString(string){
    return !(string === "")
}

