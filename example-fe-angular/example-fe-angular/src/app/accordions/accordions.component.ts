import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-accordions',
  templateUrl: './accordions.component.html',
  styleUrls: ['./accordions.component.css']
})
export class AccordionsComponent implements OnInit {

  constructor() { }

  @Input("accordions") accordions : any;

  ngOnInit() {
    setTimeout(function () {
      console.log("Init timeout")
    },3000)
  }

}
