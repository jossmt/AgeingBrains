import {Component, OnInit} from '@angular/core';
import {AppService} from "../app.service";

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {

  stepResultsData = [];
  activatedOutputNodes = [];

  constructor(private as: AppService) {
    this.as.getResults().subscribe(data => {

      Object.keys(data['learningStepCount']).forEach(key => this.stepResultsData.push("Step:" + key + " Count: " + data['learningStepCount'][key]));
      this.activatedOutputNodes = data['activatedOutputNodes'];
      console.log(data);
    });
  }

  ngOnInit(): void {
  }

}
