import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search-geo',
  templateUrl: './search-geo.component.html',
  styleUrls: ['./search-geo.component.css']
})
export class SearchGeoComponent implements OnInit {

  placeLon: any;
  placeLat: any;
  city: any;
  accessLocation: string = "";

  constructor() { }

  ngOnInit(): void {
  }

  placeSelected(place: any){
    this.accessLocation = place.properties.city;
    this.accessLocation = this.accessLocation.replace(" ","_");
    this.placeLat = place.properties.lat;
    this.placeLon = place.properties.lon;
    this.city = place.properties.address_line1 + " " + place.properties.address_line2;

  }
}
