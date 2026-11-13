export function CidadaosPage() {
  return (
    <div className="space-y-4">
      <h1 className="text-2xl font-semibold text-emerald-900">Cidadãos</h1>
      <p className="text-slate-600">
        Seção para <strong>cadastrar</strong> e <strong>listar</strong> cidadãos
        (issue #24). O cliente HTTP já aponta para{" "}
        <code className="rounded bg-slate-100 px-1 text-sm">/api/cidadaos</code>.
      </p>
      <p className="rounded-lg border border-dashed border-slate-300 bg-slate-50 px-4 py-3 text-sm text-slate-500">
        Próximo passo: formulário e tabela consumindo{" "}
        <code className="text-xs">listarCidadaos()</code> em{" "}
        <code className="text-xs">src/api/cidadaos.ts</code>.
      </p>
    </div>
  );
}
