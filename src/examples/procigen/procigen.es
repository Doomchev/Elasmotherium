import gui

singleton project {
  
}

Window("Procigen", GUI{
	ListView(Link(project.functions), projectView) {
    width: 200
    onSelect: () {
      selectedObject = project
      propertiesView.update()
    }
  }
	ListView(Link(properties.functions), projectView) {
    width: 200
    onSelect: () {
      selectedObject = project
      propertiesView.update()
    }
  }
}).open()