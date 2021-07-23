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
  activatedOutputNodeCount = [];

  constructor(private as: AppService) {
    this.as.getResults().subscribe(data => {
      console.log(data);

      Object.keys(data['learningStepCount']).forEach(key => this.stepResultsData.push("Step:" + key + " Count: " + data['learningStepCount'][key]));
      this.activatedOutputNodes = data['activatedOutputNodes'];

      Object.keys(data['activatedOutputNodeCount']).forEach(key => this.activatedOutputNodeCount.push("Output Node:" + key + " Count: " + data['activatedOutputNodeCount'][key]));
    });
  }

  ngOnInit(): void {
  }

}
