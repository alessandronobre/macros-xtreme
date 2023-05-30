
//Seleciona os itens clicado
var menuItem = document.querySelectorAll('.item-menu')

function selectLink(){
    menuItem.forEach((item)=>
        item.classList.remove('ativo')
    )
    this.classList.add('ativo')
}

menuItem.forEach((item)=>
    item.addEventListener('click', selectLink)
)

//Expandir o menu

var btnExp = document.querySelector('#btn-exp')
var menuSide = document.querySelector('.menu-lateral')

btnExp.addEventListener('click', function(){
    menuSide.classList.toggle('expandir')
})

  document.addEventListener("DOMContentLoaded", function() {
    var homeItem = document.querySelector(".home-item");
    var form = document.querySelector("form");

    homeItem.addEventListener("click", function() {
      form.classList.add("mostrar-formulario");
    });

    var itensMenu = document.querySelectorAll(".item-menu:not(.home-item)");

    itensMenu.forEach(function(item) {
      item.addEventListener("click", function() {
        form.classList.remove("mostrar-formulario");
      });
    });
  });