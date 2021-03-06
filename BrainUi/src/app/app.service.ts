import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  rootUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  initiateGraph(data) {
    return this.http.post(this.rootUrl + "/initiate", data,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      });
  }

  getGraph() {
    return this.http.get(this.rootUrl + "/graph", {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

  setThreshold(value: number) {
    return this.http.get(this.rootUrl + "/updateThreshold/" + value, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

  generateActivationPatterns() {
    return this.http.get(this.rootUrl + "/generateActivationPatterns", {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

  triggerLearning(learningParams: any) {
    return this.http.post(this.rootUrl + "/learning/start", learningParams, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }

  getResults() {
    return this.http.get(this.rootUrl + "/results", {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    });
  }
}
