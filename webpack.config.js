const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');
const HtmlWebpackPlugin=require('html-webpack-plugin');


module.exports = {
    mode: 'development',
    devtool: 'source-map',
    entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),

    // devServer: {
    //     contentBase: './dist',
    //     compress: true,
    //     port: 8000,
    //     allowedHosts: [
    //         'localhost:8080'
    //     ]
    // },

    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'src', 'main', 'resources','static', 'outjs'),
        publicPath: "/"
    },


    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            template: "./src/main/resources/templates/index.html"
        }),
        // new CleanWebpackPlugin([
        //         "./src/main/resources/static/outjs/*.*"
        // ])
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}