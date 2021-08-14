import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../app.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.scss']
})
export class ViewComponent {

  initialWeightForm: FormGroup;
  thresholdForm: FormGroup;
  learningParametersForm: FormGroup;
  graph: any;
  nodes = [];
  edges = [];
  activationPatternDetails: any;
  resultsData = [];
  loading = true;
  learningLoading = true;
  thresholdSet = false;

  //learning parameters
  youngLearning = true;

  constructor(private as: AppService,
              private fb: FormBuilder,
              private snackbar: MatSnackBar) {
    this.initialWeightForm = this.fb.group({
      nodeSize: [10, Validators.required],
      activatedNodeSize: [3, Validators.required],
      weightDistributionType: ['RANDOM', Validators.required]
    });

    this.thresholdForm = this.fb.group({
      weightThreshold: [0.43, Validators.required]
    });

    this.learningParametersForm = this.fb.group({
      youngLearning: [this.youngLearning, Validators.required],
      inputNodeSelection: ['HIGHEST', Validators.required],
      firstEdgeSelection: ['HIGHEST', Validators.required],
      secondEdgeSelection: ['HIGHEST', Validators.required]
    })
  }

  refresh() {
    this.as.getGraph().subscribe(data => this.graph = data);
  }

  setYoungLearning(val: boolean) {
    this.youngLearning = val;
  }

  submit() {
    this.thresholdSet = false;
    this.as.initiateGraph(this.initialWeightForm.getRawValue()).subscribe(data => {
      this.graph = data;
      this.edges = this.graph.edges.map(e => {
        this.nodes.push({
          id: String(e.inputNode.id),
          label: String(e.inputNode.id),
          data: {
            color: 'white'
          }
        });
        this.nodes.push({
          id: String(e.outputNode.id),
          label: String(e.outputNode.id),
          data: {
            color: 'white'
          }
        });
      });
      let index = 0;
      this.edges = this.graph.edges.map(e => {
        index++;
        return {
          source: String(e.inputNode.id),
          target: String(e.outputNode.id),
          label: String(e.weight)
        }
      });
      this.loading = false;
    })
  }

  updateThreshold() {
    this.as.setThreshold(this.thresholdForm.get('weightThreshold').value).subscribe(() => {
      this.snackbar.open("Threshold Updated", "close", {duration: 2000});
      this.thresholdSet = true;
    });
  }

  generateActivationPatterns() {
    this.activationPatternDetails = {};
    return this.as.generateActivationPatterns().subscribe(data => this.activationPatternDetails = data);
  }

  startLearning() {
    this.learningParametersForm.patchValue({
      youngLearning: this.youngLearning
    });
    this.resultsData = [];
    this.learningLoading = true;
    console.log(this.learningParametersForm.getRawValue());
    this.as.triggerLearning(this.learningParametersForm.getRawValue()).subscribe(data => {
      console.log(data);
      Object.keys(data['learningStepCount']).forEach(key => this.resultsData.push("Step:" + key + " Count: " + data['learningStepCount'][key]));
      this.learningLoading = false;
      console.log(this.resultsData);
    });
  }

}
