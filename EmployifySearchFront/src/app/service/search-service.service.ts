import { Injectable } from '@angular/core';
import { SearchDTO } from '../dto/SearchDTO';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  private baseUrl = "http://localhost:9099"
  private searchServiceUrl = "/api/v1/search"
  private standardSearchUrl = "/standard"

  constructor(private http: HttpClient) { }

  searchStandard(searchDTO: SearchDTO) {
    return this.http.post(`${this.baseUrl}${this.searchServiceUrl}${this.standardSearchUrl}`, searchDTO)
  }


}
