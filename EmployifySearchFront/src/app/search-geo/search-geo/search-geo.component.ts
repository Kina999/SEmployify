import { Component, OnInit } from '@angular/core';
import { SearchGeoDTO } from 'src/app/dto/SearchGeoDTO';
import { SearchResultDTO } from 'src/app/dto/SearchResultDTO';
import { SearchServiceService } from 'src/app/service/search-service.service';

@Component({
  selector: 'app-search-geo',
  templateUrl: './search-geo.component.html',
  styleUrls: ['./search-geo.component.css']
})
export class SearchGeoComponent implements OnInit {

  radius: any;
  placeLon: any;
  placeLat: any;
  city: any;
  accessLocation: string = "";
  searchResult: SearchResultDTO[] = [];

  constructor(private searchService: SearchServiceService) { }

  ngOnInit(): void {

  }

  search() {
    let searchDTO: SearchGeoDTO = { "longitude": this.placeLon, "latitude": this.placeLat , "radius": this.radius};
    this.searchService.searchGeo(searchDTO).subscribe((data: any) => {
      this.searchResult = data;
    }, (err) => {
      alert("An error occurred, please try again...");
    })
  }

  downloadCv(i: number) {
    const link = document.createElement('a');
    link.setAttribute('target', '_blank');
    link.setAttribute('href', '.../../assets/files/'+this.searchResult[i].cvPath);
    link.setAttribute('download', this.searchResult[i].cvPath);
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

  downloadCl(i: number) {
    const link = document.createElement('a');
    link.setAttribute('target', '_blank');
    link.setAttribute('href', '.../../assets/files/'+this.searchResult[i].clPath);
    link.setAttribute('download', this.searchResult[i].clPath);
    document.body.appendChild(link);
    link.click();
    link.remove();
  }
  placeSelected(place: any){
    this.accessLocation = place.properties.city;
    this.accessLocation = this.accessLocation.replace(" ","_");
    this.placeLat = place.properties.lat;
    this.placeLon = place.properties.lon;
    this.city = place.properties.address_line1 + " " + place.properties.address_line2;

  }
}
