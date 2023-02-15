import { Component, OnInit } from '@angular/core';
import { SearchServiceService } from 'src/app/service/search-service.service';
import { SearchDTO } from 'src/app/dto/SearchDTO';
import { StatisticResultDTO } from 'src/app/dto/StatisticResultDTO';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  requestStatisticResult: StatisticResultDTO[] = [];
  candidateStatisticResult: StatisticResultDTO[] = [];
  companyStatisticResult: StatisticResultDTO[] = [];

  constructor(private searchService: SearchServiceService) { }

  ngOnInit(): void {
    let searchDTO: SearchDTO = { "value": "CITY", "field": "message", "isPhrase": false};
    this.searchService.searchRequestsStatistics(searchDTO).subscribe((data: any) => {
      this.requestStatisticResult = data;
    }, (err) => {
      alert("An error occurred, please try again...");
    })
    let searchCompanyDTO: SearchDTO = { "value": "COMPANY", "field": "message", "isPhrase": false};
    this.searchService.searchRequestsStatistics(searchCompanyDTO).subscribe((data: any) => {
      this.companyStatisticResult = data;
    }, (err) => {
      alert("An error occurred, please try again...");
    })
    let searchCandidateDTO: SearchDTO = { "value": "EMPLOYEE", "field": "message", "isPhrase": false};
    this.searchService.searchRequestsStatistics(searchCandidateDTO).subscribe((data: any) => {
      this.candidateStatisticResult = data;
    }, (err) => {
      alert("An error occurred, please try again...");
    })
  }

}
