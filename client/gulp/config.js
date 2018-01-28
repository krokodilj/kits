'use strict'
console.log("loaded")
exports.paths= {
	scripts: './src/**/*.js',
    styles: ['./src/app/**/*.css', './app/**/*.scss'],
    index: './src/index.html',
    partials: ['./src/**/*.html', '!./src/index.html'],
    assets:['./src/assets/**'],
    distDev: './dist.dev',
    distProd: './dist.prod',
    distScriptsProd: './dist.prod/scripts',
    scriptsDevServer: 'devServer/**/*.js'
}