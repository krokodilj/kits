var gulp = require('gulp');
var plugins = require('gulp-load-plugins')();
var del = require('del');   
var bowerFiles = require('main-bower-files');
var print = require('gulp-print');
var Q = require('q');

'use strict';

var gulp = require('gulp');
var fse = require('fs-extra');

fse.walkSync('./gulp').filter(function (file)
    {
        return (/\.(js)$/i).test(file);
    }
).map(function (file)
    {
        require('./' + file);
    }
);