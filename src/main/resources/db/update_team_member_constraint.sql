-- Drop existing constraint if it exists
ALTER TABLE team_member DROP CONSTRAINT IF EXISTS team_member_status_check;

-- Add new constraint that includes DECLINED status
ALTER TABLE team_member ADD CONSTRAINT team_member_status_check 
CHECK (status::text = ANY (ARRAY['PENDING'::text, 'ACCEPTED'::text, 'DECLINED'::text])); 