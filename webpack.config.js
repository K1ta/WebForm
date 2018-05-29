const path = require('path');

var fooConfig = Object.assign({}, {
    entry: './src/main/resources/static/scripts/index.js',
    output: {
        path: path.resolve(__dirname, './public/dist/scripts'),
        filename: "index.js"
    }
});
var barConfig = Object.assign({},{
    entry: './src/main/resources/static/scripts/login.js',
    output: {
        path: path.resolve(__dirname, './public/dist/scripts'),
        filename: "login.js"
    }
});

// Return Array of Configurations
module.exports = [
    fooConfig, barConfig
];
