import { User } from './User';

export interface Tag {
    id: string;
    name: string;
    user: User;
    kind: string;
}
