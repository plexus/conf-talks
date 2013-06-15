// <script src="https://rawgithub.com/jasondavies/d3-cloud/master/lib/d3/d3.js"></script>
// <script src="https://rawgithub.com/jasondavies/d3-cloud/master/d3.layout.cloud.js"></script>

  var fill = d3.scale.category20();

  d3.layout.cloud().size([900, 600])
      .words([
"SQL",
"HTML",
"SCSS",
"HTTP",
"Javascript",
"Coffeescript",
"JSON",
"XML",
"YAML",
"Ruby",
"Regex",
"URI",
"MIME",
"CSS",
"SASS"

      ].map(function(d) {
        return {text: d, size: 10 + Math.random() * 90};
      }))
      .rotate(function() { return ~~(Math.random() * 2) * 90; })
      .font("Impact")
      .fontSize(function(d) { return d.size; })
      .on("end", draw)
      .start();

  function draw(words) {
    d3.select(".lang_words").append("svg")
        .attr("width", 900)
        .attr("height", 600)
      .append("g")
        .attr("transform", "translate(450,300)")
      .selectAll("text")
        .data(words)
      .enter().append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("font-family", "Impact")
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
  }
