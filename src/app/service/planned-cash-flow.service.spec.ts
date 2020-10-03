import { TestBed } from '@angular/core/testing';

import { PlannedCashFlowService } from './planned-cash-flow.service';

describe('PlannedCashFlowService', () => {
  let service: PlannedCashFlowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlannedCashFlowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
