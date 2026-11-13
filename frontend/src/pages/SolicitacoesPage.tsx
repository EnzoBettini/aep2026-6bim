import { useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";

export function SolicitacoesPage() {
  const [id, setId] = useState("");
  const navigate = useNavigate();

  function abrirDetalhe(e: FormEvent) {
    e.preventDefault();
    const trim = id.trim();
    if (trim) {
      navigate(`/solicitacoes/${encodeURIComponent(trim)}`);
    }
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-semibold text-emerald-900">
          Solicitações
        </h1>
        <p className="mt-2 text-slate-600">
          Ocorrências urbanas vinculadas a um cidadão. A listagem por cidadão
          entra na issue #25.
        </p>
      </div>

      <section className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <h2 className="text-lg font-medium">Detalhe e histórico</h2>
        <p className="mt-1 text-sm text-slate-500">
          Informe o ID da solicitação (MongoDB) para ver status e histórico.
        </p>
        <form onSubmit={abrirDetalhe} className="mt-4 flex flex-wrap gap-2">
          <input
            type="text"
            value={id}
            onChange={(e) => setId(e.target.value)}
            placeholder="ID da solicitação"
            className="min-w-[16rem] flex-1 rounded-lg border border-slate-300 px-3 py-2 text-sm"
          />
          <button
            type="submit"
            className="rounded-lg bg-emerald-700 px-4 py-2 text-sm font-medium text-white hover:bg-emerald-800"
          >
            Abrir detalhe
          </button>
        </form>
      </section>
    </div>
  );
}
