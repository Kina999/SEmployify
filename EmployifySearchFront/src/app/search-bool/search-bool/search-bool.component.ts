import { Component, OnInit } from '@angular/core';
import { SearchBoolDTO } from 'src/app/dto/SearchBoolDTO';
import { SearchResultDTO } from 'src/app/dto/SearchResultDTO';
import { SearchServiceService } from 'src/app/service/search-service.service';

@Component({
  selector: 'app-search-bool',
  templateUrl: './search-bool.component.html',
  styleUrls: ['./search-bool.component.css']
})
export class SearchBoolComponent implements OnInit {

  firstSelect: number = 0;
  secondSelect: number = 0;
  firstString: string = '';
  secondString: string = '';
  isOr: number = 0;
  searchResult: SearchResultDTO[] = [];
  firstPhrase: boolean = false;
  secondPhrase: boolean = false;

  constructor(private searchService: SearchServiceService) { }

  ngOnInit(): void {
  }

  search() {
    var firstFieldV = '';
    var firstValueV = '';
    if(this.firstSelect == this.secondSelect){alert("Please choose different fields")}
    if (this.firstSelect == 0) { firstFieldV = "cvContent"; firstValueV = this.firstString; }
    else if (this.firstSelect == 1) { firstFieldV = "coverLetterContent"; firstValueV = this.firstString; }
    else if (this.firstSelect == 2) { firstFieldV = "firstName"; firstValueV = this.firstString; }
    else if (this.firstSelect == 3) { firstFieldV = "lastName"; firstValueV = this.firstString; }
    else if (this.firstSelect == 4) { firstFieldV = "education"; firstValueV = this.firstString; }
    var secondFieldV = '';
    var secondValueV = '';
    if (this.secondSelect == 0) { secondFieldV = "cvContent"; secondValueV = this.secondString; }
    else if (this.secondSelect == 1) { secondFieldV = "coverLetterContent"; secondValueV = this.secondString; }
    else if (this.secondSelect == 2) { secondFieldV = "firstName"; secondValueV = this.secondString; }
    else if (this.secondSelect == 3) { secondFieldV = "lastName"; secondValueV = this.secondString; }
    else if (this.secondSelect == 4) { secondFieldV = "education"; secondValueV = this.secondString; }
    var isOr = false;
    if(this.isOr == 1){isOr = true;}
    let searchBoolDTO: SearchBoolDTO = { "firstValue": firstValueV, "firstField": firstFieldV, "secondValue": secondValueV, "secondField": secondFieldV,"isOr": isOr, "isFirstPhrase": this.firstPhrase, "isSecondPhrase": this.secondPhrase};
    this.searchService.searchBool(searchBoolDTO).subscribe((data: any) => {
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
