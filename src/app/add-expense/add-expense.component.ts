import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TagService } from '../service/tag.service';
import { PlannedCashFlowService } from '../service/planned-cash-flow.service';
import { PlannedCashFlow } from 'src/model/PlannedCashFlow';
import { Tag } from 'src/model/Tag';

@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.css']
})
export class AddExpenseComponent implements OnInit {

  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private tagService: TagService,
              private plannedCashFlowService: PlannedCashFlowService){
    this.contactForm = fb.group({
      amount : '',
      note : '',
      tags : '',
      flows: '',
      flowSelectedValue: '',
      tagSelectedValue: ''
    });
  }

  flows: PlannedCashFlow[];
  tags: Tag[];

  ngOnInit(): void {
    this.getAllTags('NEGATIVE');
    this.getAllPlannedCashFlows();

  }

  public getAllTags(kind: string): void {
    this.tagService.getAllTags(kind).subscribe(tagsFromAPI => this.tags = tagsFromAPI);
  }

  public getAllPlannedCashFlows(): void {
    this.plannedCashFlowService.getAllPlannedCashFlow().subscribe(plannedCashFlows => this.flows = plannedCashFlows);
  }

}
