'use strict';

var gulp = require('gulp');
var bowerFiles = require('main-bower-files');
var conf = require('./config');
var del = require('del');
var es = require('event-stream'); 
var plugins = require('gulp-load-plugins')({
    //pattern: []
});

var pipes ={}

pipes.orderedVendorScripts = function() {  
    return plugins.order(['jquery.js', 'angular.js']);
};

pipes.orderedAppScripts = function() {  
    return plugins.angularFilesort();
};

pipes.builtVendorScriptsDev = function() {  
    return gulp.src(bowerFiles())
        .pipe(gulp.dest('dist.dev/bower_components'));
};

pipes.builtAppScriptsDev = function() {  
    return gulp.src(conf.paths.scripts)
        .pipe(gulp.dest(conf.paths.distDev));
};

pipes.builtStylesDev = function() {  
    return gulp.src(conf.paths.styles)
        .pipe(gulp.dest(conf.paths.styles));
};

pipes.builtPartialsDev = function(){
	return gulp.src(conf.paths.partials)
		.pipe(gulp.dest(conf.paths.distDev));
};

pipes.builtIndexDev = function(){
	var vendorScripts = pipes.builtVendorScriptsDev()
        .pipe(pipes.orderedVendorScripts());

   	var appScripts = pipes.builtAppScriptsDev()
        .pipe(pipes.orderedAppScripts());

    return gulp.src(conf.paths.index)
        .pipe(gulp.dest(conf.paths.distDev)) // write first to get relative path for inject
         .pipe(plugins.inject(vendorScripts, {relative: true, name: 'bower'}))
         .pipe(plugins.inject(appScripts, {relative: true}))
        .pipe(gulp.dest(conf.paths.distDev));
};

pipes.builtAssetsDev = function() {  
    return gulp.src(conf.paths.assets)
        .pipe(gulp.dest('dist.dev/assets'));
};

gulp.task('clean-dev', function(done) {
    del(conf.paths.distDev);
    done();
});

gulp.task('build-dev',function(done){
	es.merge(pipes.builtIndexDev(), pipes.builtPartialsDev(), pipes.builtAssetsDev());
	done();
});

gulp.task('watch-dev',function(done){
	plugins.nodemon({
         script: 'server.js',
         ext: 'html js css',
         watch: ['src/'],
         env: {NODE_ENV : 'development'},
         tasks:['build-dev']
         })
     

	done();
});


