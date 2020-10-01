import { Tag } from './Tag';
import { PlannedCashFlow } from './PlannedCashFlow';
import { User } from './User';

export interface Expenditure {
    id: string;
    amount: number;
    expenditureDate: string;
    tag: Tag;
    plannedCashFlow: PlannedCashFlow;
    note: string;
    user: User;
}