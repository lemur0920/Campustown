#### All default configuration options to customize the tree view.

$('#mytree').bstree({
  DEBUG: false,
  dataSource: '',                                      // the source control id
  initValues: '',
  dataSeparator: ':',                                     // the separator used for the exploded data
  chevronOpened: '<i class="fa fa-toggle-down fa-lg"></i>',    // the icon used for an opened node
  chevronClosed: '<i class="fa fa-toggle-right fa-lg"></i>',   // the icon used for a closed node
  isExpanded: false,                                 // sets if nodes are initially expanded
  nodeClass: pluginName + '-node',                      //  generic node class
  compositeClass: pluginName + '-composite',                 //  composite node class
  leafClass: pluginName + '-leaf',                      //  leaf node class
  childrenClass: pluginName + '-children',                  //  node children class
  innerContainerClass: pluginName + '-inner-container',
  chevronClass: pluginName + '-chevron',                   //  chevron icon class
  labelClass: pluginName + '-label',                     //  label class
  labelContainerClass: pluginName + '-label-container',
  iconClass: pluginName + '-icon',                      //  label icon class
  expandedClass: pluginName + '-expanded',                  //  opened node class
  closedClass: pluginName + '-closed',                    //  closed node class
  checkboxClass: pluginName + '-checkbox',                  //  checkbox class
  incompleteClass: pluginName + '-incomplete',                //  incomplete node class
  vLineClass: pluginName + '-vline',
  dataClass: pluginName + '-data',
  openTitle: 'Open',
  closeTitle: 'Close',
  checkboxTitle: 'Do action',
  updateNodeTitle: null,
  onDataPush: null
})