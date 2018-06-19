import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  name = "test";
  showError : boolean = false;
  cards = [{
    img:"assets/img/card.jpg",
    title:"Card title",
    text:"Some quick example text to build on the card title and make up the bulk of the card's content."
  },
    {
      img:"assets/img/card.jpg",
      title:"Card title 2",
      text:"Some quick example text to build on the card title and make up the bulk of the card's content."
    },
    {
      img:"assets/img/card.jpg",
      title:"Card title 3",
      text:"Some quick example text to build on the card title and make up the bulk of the card's content."
    }];

  showErrorClick() : void{
    this.showError = !this.showError;
  }

  constructor() { }

  ngOnInit() {
  }

}
//ng g component index
