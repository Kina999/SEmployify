import { Component, OnInit } from '@angular/core';
import { SearchServiceService } from 'src/app/service/search-service.service';
import { SearchDTO } from 'src/app/dto/SearchDTO';
import { SearchResultDTO } from 'src/app/dto/SearchResultDTO';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  select: any = 0;
  searchString: any;
  name: string = '';
  lastName: string = '';
  searchResult: SearchResultDTO[] = [];
  phrase: boolean = false;

  constructor(private searchService: SearchServiceService) { }

  ngOnInit(): void {

  }

  search() {
    var fieldV = '';
    var valueV = '';
    if (this.select == 0) { fieldV = "cvContent"; valueV = this.searchString; }
    else if (this.select == 1) { fieldV = "coverLetterContent"; valueV = this.searchString; }
    else if (this.select == 2) { fieldV = "firstNameAndLastName"; valueV = this.name + "!" + this.lastName; }
    else if (this.select == 3) { fieldV = "education"; valueV = this.searchString; }
    let searchDTO: SearchDTO = { "value": valueV, "field": fieldV , "isPhrase": this.phrase};
    this.searchService.searchStandard(searchDTO).subscribe((data: any) => {
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

}
