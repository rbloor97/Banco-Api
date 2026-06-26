if(typeof global !== 'undefined' && typeof Uint8Array !== 'undefined'){
    Object.setPrototypeOf(Buffer.prototype, Uint8Array.prototype);
}

const originalInstanceof = Extension => {
    return (obj) => obj instanceof Extension;
};