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