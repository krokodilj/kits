var express        = require('express');
var app            = express();
var methodOverride = require('method-override');

var port = process.env.PORT || 8081; // set our port

var staticdir = process.env.NODE_ENV === 'production' ? 'dist.prod' : 'dist.dev'; // get static files dir


app.use(methodOverride('X-HTTP-Method-Override')); // override with the X-HTTP-Method-Override header in the request. simulate DELETE/PUT
app.use(express.static(__dirname + '/' + staticdir));

app.listen(port);                  
console.log('Starting sever on port ' + port);       
console.log(staticdir);
exports = module.exports = app;       